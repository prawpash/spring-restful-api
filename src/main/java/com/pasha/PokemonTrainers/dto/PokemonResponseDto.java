package com.pasha.PokemonTrainers.dto;

import com.pasha.PokemonTrainers.model.Trainer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonResponseDto {
    private Integer id;
    private String name;
    private String ability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer trainer_id;
}
