package com.pasha.PokemonTrainers.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPokemonDto {
    private String name;

    private String ability;

    private Integer page;

    private Integer size;
}
