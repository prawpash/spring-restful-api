package com.pasha.PokemonTrainers.service;

import java.util.List;
import java.util.Optional;
import com.pasha.PokemonTrainers.dto.TrainerDto;
import com.pasha.PokemonTrainers.dto.UpdateTrainerDto;

public interface TrainerService {
    List<TrainerDto> findAllTrainers();
    
    TrainerDto findTrainerById(Integer id);

    Optional<Trainer> findTrainerByUsername(String username);

    Boolean checkIfUsernameExists(String username);

    TrainerDto storeTrainer(TrainerDto trainerDto);

    UpdateTrainerDto updateTrainer(UpdateTrainerDto trainerDto);

    void deleteTrainerById(Integer id);
}
