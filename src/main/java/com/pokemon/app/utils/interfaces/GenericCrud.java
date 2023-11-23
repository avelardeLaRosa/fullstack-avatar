package com.pokemon.app.utils.interfaces;

import com.pokemon.app.utils.ApiResponse;

public interface GenericCrud<T> {

    ApiResponse<T> add(T t);

    ApiResponse<T> update(T t);

    ApiResponse<T> get(String id);

    void delete(String id);
}
