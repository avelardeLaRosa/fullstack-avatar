package com.pokemon.app.controllers;

import com.pokemon.app.dtos.FavoritesPokemonDTO;
import com.pokemon.app.services.IPokemonService;
import com.pokemon.app.utils.ApiResponse;
import com.pokemon.app.utils.Messages;
import com.pokemon.app.utils.Pagination;
import com.pokemon.app.utils.interfaces.ServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pokemon")
@CrossOrigin(origins = "http://localhost:4200")
public class PokemonRESTController {

    private final IPokemonService pokemonService;

    @Autowired
    public PokemonRESTController(IPokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<FavoritesPokemonDTO>> getPokemones(
            @RequestParam(value = "pageNum", defaultValue = ServiceConstants.PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = ServiceConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "orderBy", defaultValue = ServiceConstants.DEFAULT_ORDER_BY, required = false) String orderBy,
            @RequestParam(value = "sortBy", defaultValue = ServiceConstants.DEFAULT_SORT_BY, required = false) String sortDir
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("pageNum", pageNum);
        parameters.put("pageSize", pageSize);
        parameters.put("orderBy", orderBy);
        parameters.put("sortBy", sortDir);
        ApiResponse response = new ApiResponse();
        Pagination pokemones = this.pokemonService.getPagination(parameters);
        response.success(Messages.OK.getCode(), Messages.OK.getMessage(), pokemones);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/favorite")
    public ResponseEntity<ApiResponse<List<FavoritesPokemonDTO>>> saveFavorites(
            @RequestBody List<FavoritesPokemonDTO> favoritesPokemonDTOS
    ) {
        ApiResponse<List<FavoritesPokemonDTO>> response;
        if (favoritesPokemonDTOS.isEmpty()) {
            response = new ApiResponse<List<FavoritesPokemonDTO>>().error(400, ServiceConstants.DTO_EMPTY);
            return new ResponseEntity<>(response, response.getCode());
        }
        response = this.pokemonService.addFavorites(favoritesPokemonDTOS);
        if (response.getExitoso().equals(false)) {
            return new ResponseEntity<>(response, response.getCode());
        }
        return new ResponseEntity<>(response, response.getCode());
    }

}
