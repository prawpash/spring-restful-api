package com.pasha.PokemonTrainers.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.pasha.PokemonTrainers.model.Pokemon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDto {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Pokemon> pokemons;
}
