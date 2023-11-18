package com.pasha.PokemonTrainers.dto;

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
public class InputTrainerDto {
    @NotBlank(message = "Trainer name should not be empty")
    private String name;
    @NotBlank(message = "Trainer username should not be empty")
    private String username;
    @NotBlank(message = "Trainer password should not be empty")
    private String password;
}
