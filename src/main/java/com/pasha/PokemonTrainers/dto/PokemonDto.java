package com.pasha.PokemonTrainers.dto;

import java.time.LocalDateTime;

import com.pasha.PokemonTrainers.model.Trainer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonDto {
    private Integer id;
    @NotBlank(message = "Pokemon Name should not be empty")
    private String name;
    @NotBlank(message = "Pokemon Ability should not be empty")
    private String ability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Min(value = 1, message = "Pokemon Trainer should not be empty")
    private Integer trainer_id;
    private Trainer trainer;
}
