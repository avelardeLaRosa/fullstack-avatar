package com.pokemon.app.services.impl;

import com.pokemon.app.dtos.FavoritesPokemonDTO;
import com.pokemon.app.entities.FavoritesPokemonEntity;
import com.pokemon.app.repositories.IPokemonRepository;
import com.pokemon.app.services.IPokemonService;
import com.pokemon.app.utils.ApiResponse;
import com.pokemon.app.utils.Pagination;
import com.pokemon.app.utils.interfaces.ServiceConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PokemonServiceImpl implements IPokemonService {

    private final IPokemonRepository pokemonRepository;
    @Resource
    private IPokemonService pokemonService;

    @Autowired
    public PokemonServiceImpl(IPokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Transactional(rollbackOn = {
            RuntimeException.class
    })
    @Override
    public ApiResponse<FavoritesPokemonDTO> add(FavoritesPokemonDTO favoritesPokemonDTO) {
        Optional<FavoritesPokemonEntity> optionalPokemon = this.pokemonRepository.findPokemon(
                ServiceConstants.CREATED,
                favoritesPokemonDTO.getServiceId()
        );
        if (optionalPokemon.isPresent()) {
            return getErrorApiResponse(400, ServiceConstants.POKEMON_EXIST);
        }

        FavoritesPokemonEntity pokemonEntity = new FavoritesPokemonEntity();
        favoritesPokemonDTO.setStatus(ServiceConstants.CREATED);
        favoritesPokemonDTO.setUniqueIdentifier(UUID.randomUUID().toString());
        pokemonEntity.setEntity(favoritesPokemonDTO);
        return saveEntityAndApiResponse(201, ServiceConstants.POKEMON_SAVED, pokemonEntity);
    }


    @Override
    public Pagination<FavoritesPokemonDTO> getPagination(Map<String, Object> parameters) {
        Sort sort = ordenarPor(parameters.get(ServiceConstants.PARAM_ORDER_BY).toString(), parameters.get(ServiceConstants.PARAM_SORT_BY).toString());
        Pageable pageable = PageRequest.of(Integer.parseInt(parameters.get(ServiceConstants.PARAM_PAGE_NUMBER).toString()), Integer.parseInt(parameters.get(ServiceConstants.PARAM_PAGE_SIZE).toString()), sort);
        Page<FavoritesPokemonEntity> pokemones = this.pokemonRepository.getEntities(ServiceConstants.CREATED, pageable);
        List<FavoritesPokemonDTO> contenido = pokemones.getContent().stream().map(FavoritesPokemonEntity::getDTO).collect(Collectors.toList());
        Pagination paginacion = new Pagination();
        paginacion.setPageNumber(pokemones.getNumber());
        paginacion.setPageSize(pokemones.getSize());
        paginacion.setList(contenido);
        paginacion.setTotalElements(pokemones.getTotalElements());
        paginacion.setTotalPages(pokemones.getTotalPages());
        paginacion.setLastRow(pokemones.isLast());
        return paginacion;
    }

    public Sort ordenarPor(String ordernarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordernarPor).ascending() : Sort.by(ordernarPor).descending();
        return sort;
    }

    @Override
    public ApiResponse<List<FavoritesPokemonDTO>> addFavorites(List<FavoritesPokemonDTO> dtoList) {
        List<FavoritesPokemonDTO> pokemonResponseList = new ArrayList<>();
        try {
            dtoList.forEach(pokemon -> {
                ApiResponse<FavoritesPokemonDTO> response = this.pokemonService.add(pokemon);
                if (response.getExitoso().equals(false)) {
                    throw new RuntimeException(response.getMessages());
                }
                pokemonResponseList.add(response.getData());
            });
        } catch (RuntimeException r) {
            return getErrorListApiResponse(400, r.getMessage());
        }
        return getSucessListApiResponse(200, ServiceConstants.POKEMON_LIST_SAVED, pokemonResponseList);
    }

    @Override
    public ApiResponse<FavoritesPokemonDTO> update(FavoritesPokemonDTO favoritesPokemonDTO) {
        return null;
    }

    @Override
    public ApiResponse<FavoritesPokemonDTO> get(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }


    public ApiResponse<FavoritesPokemonDTO> saveEntityAndApiResponse(Integer code, String message, FavoritesPokemonEntity entity) {
        return new ApiResponse<FavoritesPokemonDTO>().success(code, message, this.pokemonRepository.save(entity).getDTO());
    }


    public ApiResponse<FavoritesPokemonDTO> getSucessApiResponse(Integer code, String message, FavoritesPokemonDTO data) {
        return new ApiResponse<FavoritesPokemonDTO>().success(code, message, data);
    }

    public ApiResponse<FavoritesPokemonDTO> getErrorApiResponse(Integer code, String message) {
        return new ApiResponse<FavoritesPokemonDTO>().failed(code, message);
    }

    public ApiResponse<List<FavoritesPokemonDTO>> getSucessListApiResponse(Integer code, String message, List<FavoritesPokemonDTO> data) {
        return new ApiResponse<List<FavoritesPokemonDTO>>().success(code, message, data);
    }

    public ApiResponse<List<FavoritesPokemonDTO>> getErrorListApiResponse(Integer code, String message) {
        return new ApiResponse<List<FavoritesPokemonDTO>>().failed(code, message);
    }


}
