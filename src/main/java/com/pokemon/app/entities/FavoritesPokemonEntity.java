package com.pokemon.app.entities;

import com.pokemon.app.dtos.FavoritesPokemonDTO;
import com.pokemon.app.utils.entities.AuditoryEntity;
import com.pokemon.app.utils.interfaces.DTOManagment;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "t_favorite_pokemon")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FavoritesPokemonEntity extends AuditoryEntity implements DTOManagment<FavoritesPokemonDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pokemon")
    private Long id;
    @Column(name = "tx_service_id")
    private String serviceId;
    @Column(name = "tx_name")
    private String name;
    @Column(name = "tx_img")
    private String img;
    @Column(name = "tx_experience")
    private String experience;
    @Column(name = "tx_weight")
    private String weight;
    @Column(name = "tx_height")
    private String height;
    @Column(name = "fl_is_favoritoe")
    private String isFavorite; //Y-N


    @Override
    public FavoritesPokemonDTO getDTO() {
        FavoritesPokemonDTO dto = new FavoritesPokemonDTO();
        
        dto.setName(this.name);
        dto.setServiceId(this.serviceId);
        dto.setName(this.name);
        dto.setImg(this.img);
        dto.setExperience(this.experience);
        dto.setWeight(this.weight);
        dto.setHeight(this.height);
        dto.setIsFavorite(this.isFavorite);

        dto.setId(getUniqueIdentifier());
        dto.setStatus(getStatus());

        dto.setCreateUser(getCreateUser());
        dto.setUpdateUser(getUpdateUser());
        dto.setDeleteUser(getDeleteUser());

        dto.setCreateDate(getCreateDate());
        dto.setUpdateDate(getUpdateDate());
        dto.setDeleteDate(getDeleteDate());
        return dto;
    }

    @Override
    public void setEntity(FavoritesPokemonDTO dto) {

        this.serviceId = dto.getServiceId();
        this.name = dto.getName();
        this.img = dto.getImg();
        this.experience = dto.getExperience();
        this.weight = dto.getWeight();
        this.height = dto.getHeight();
        this.isFavorite = dto.getIsFavorite();

        setUniqueIdentifier(dto.getUniqueIdentifier());
        setStatus(dto.getStatus());

        setCreateUser(dto.getCreateUser());
        setUpdateUser(dto.getUpdateUser());
        setDeleteUser(dto.getDeleteUser());

        setCreateDate(dto.getCreateDate());
        setUpdateDate(dto.getUpdateDate());
        setDeleteDate(dto.getDeleteDate());

    }
}
