package com.pasha.PokemonTrainers.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePokemonDto {
    private Integer id;
    @Size(message = "Pokemon Name should not be empty", min = 1)
    private String name;

    @Size(message = "Pokemon Ability should not be empty", min = 1)
    private String ability;

    @Min(value = 1, message = "Pokemon Trainer should not be empty")
    private Integer trainer_id;
}
