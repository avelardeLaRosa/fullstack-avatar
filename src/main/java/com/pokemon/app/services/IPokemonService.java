package com.pokemon.app.services;

import com.pokemon.app.dtos.FavoritesPokemonDTO;
import com.pokemon.app.utils.ApiResponse;
import com.pokemon.app.utils.Pagination;
import com.pokemon.app.utils.interfaces.GenericCrud;

import java.util.List;
import java.util.Map;

public interface IPokemonService extends GenericCrud<FavoritesPokemonDTO> {

    Pagination<FavoritesPokemonDTO> getPagination(Map<String, Object> parameters);

    ApiResponse<List<FavoritesPokemonDTO>> addFavorites(List<FavoritesPokemonDTO> dtoList);
}
