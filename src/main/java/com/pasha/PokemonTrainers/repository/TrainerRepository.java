package com.pasha.PokemonTrainers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasha.PokemonTrainers.model.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer>{
    
}
