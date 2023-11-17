package com.pasha.PokemonTrainers.service;

import java.util.List;
import com.pasha.PokemonTrainers.dto.TrainerDto;
import com.pasha.PokemonTrainers.dto.UpdateTrainerDto;

public interface TrainerService {
    List<TrainerDto> findAllTrainers();
    
    TrainerDto findTrainerById(Integer id);

    Boolean checkIfUsernameExists(String username);

    TrainerDto storeTrainer(TrainerDto trainerDto);

    UpdateTrainerDto updateTrainer(UpdateTrainerDto trainerDto);

    void deleteTrainerById(Integer id);
}
