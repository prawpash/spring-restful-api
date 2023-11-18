package com.pasha.PokemonTrainers.dto;

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
public class TrainerResponseDto {
    private Integer id;
    private String name;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
