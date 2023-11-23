package com.pokemon.app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pokemon.app.utils.dtos.AuditoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavoritesPokemonDTO extends AuditoryDTO {

    private String id;
    private String serviceId;
    private String name;
    private String img;
    private String experience;
    private String weight;
    private String height;
    private String isFavorite; //Y-N
}
