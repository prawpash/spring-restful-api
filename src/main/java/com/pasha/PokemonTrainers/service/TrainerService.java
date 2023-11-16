package com.pasha.PokemonTrainers.service;

import java.util.List;
import com.pasha.PokemonTrainers.dto.TrainerDto;

public interface TrainerService {
    List<TrainerDto> findAllTrainers();
    
    TrainerDto findTrainerById(Integer id);

    Boolean checkIfUsernameExists(String username);

    TrainerDto storeTrainer(TrainerDto trainerDto);

    TrainerDto updateTrainer(TrainerDto trainerDto);

    void deleteTrainerById(Integer id);
}
