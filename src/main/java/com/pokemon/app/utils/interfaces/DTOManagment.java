package com.pokemon.app.utils.interfaces;

public interface DTOManagment<T> {

    T getDTO();

    void setEntity(T t);
}
