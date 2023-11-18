package com.pasha.PokemonTrainers.service;

import java.util.List;
import java.util.Optional;

import com.pasha.PokemonTrainers.dto.InputTrainerDto;
import com.pasha.PokemonTrainers.dto.TrainerDto;
import com.pasha.PokemonTrainers.dto.TrainerResponseDto;
import com.pasha.PokemonTrainers.dto.UpdateTrainerDto;
import com.pasha.PokemonTrainers.model.Trainer;

public interface TrainerService {
    List<TrainerDto> findAllTrainers();
    
    TrainerDto findTrainerById(Integer id);

    Optional<Trainer> findTrainerByUsername(String username);

    Boolean checkIfUsernameExists(String username);

    TrainerDto storeTrainer(TrainerDto trainerDto);

    TrainerResponseDto storeTrainer(InputTrainerDto trainerDto);

    UpdateTrainerDto updateTrainer(UpdateTrainerDto trainerDto);

    void deleteTrainerById(Integer id);
}
