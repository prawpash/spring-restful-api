package com.pasha.PokemonTrainers.dto;

import com.pasha.PokemonTrainers.model.Pokemon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainerDto {
    private Integer id;
    @Size(message = "Name cannot be empty", min = 1)
    private String name;
    @Size(message = "Username cannot be empty", min = 1)
    private String username;
    @Size(message = "Password cannot be empty", min = 1)
    private String password;
}
