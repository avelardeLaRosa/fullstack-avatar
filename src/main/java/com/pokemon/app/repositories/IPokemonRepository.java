package com.pokemon.app.repositories;

import com.pokemon.app.entities.FavoritesPokemonEntity;
import com.pokemon.app.utils.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPokemonRepository extends JpaRepository<FavoritesPokemonEntity, Long> {

    @Query(
            value = "SELECT p FROM FavoritesPokemonEntity p " +
                    "where p.status = :status " +
                    "and p.serviceId = :serviceId "
    )
    Optional<FavoritesPokemonEntity> findPokemon(
            String status,
            String serviceId
    );

    @Query(
            value = "SELECT p FROM FavoritesPokemonEntity p " +
                    "where p.status = :status "
    )
    Page<FavoritesPokemonEntity> getEntities(
            String status,
            Pageable pageable
    );
}
