package com.pasha.PokemonTrainers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasha.PokemonTrainers.model.Pokemon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer>{
    @Query(value = "SELECT * FROM pokemons p WHERE p.trainer_id = :id", nativeQuery = true)
    List<Pokemon> findByTrainerId(@Param("id") Integer id);
}
