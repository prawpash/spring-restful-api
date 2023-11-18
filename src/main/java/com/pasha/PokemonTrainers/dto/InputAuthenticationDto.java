package com.pasha.PokemonTrainers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputAuthenticationDto {
    @NotBlank(message = "Username cannot should not be empty.")
    private String username;
    @NotBlank(message = "Password cannot should not be empty.")
    private String password;
}
