package com.pasha.PokemonTrainers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasha.PokemonTrainers.model.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer>{
    
}
