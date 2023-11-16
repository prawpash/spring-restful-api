package com.pasha.PokemonTrainers.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.pasha.PokemonTrainers.model.Pokemon;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Trainer name should not be empty")
    private String name;
    @NotBlank(message = "Trainer username should not be empty")
    private String username;
    @NotBlank(message = "Trainer password should not be empty")
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Pokemon> pokemons;
}
