package com.pasha.PokemonTrainers.dto;

import java.time.LocalDateTime;

import com.pasha.PokemonTrainers.model.Trainer;

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
    private String name;
    private String ability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Trainer trainer;
}
