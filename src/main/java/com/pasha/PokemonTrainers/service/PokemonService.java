package com.pasha.PokemonTrainers.service;

import java.util.List;

import com.pasha.PokemonTrainers.dto.*;

public interface PokemonService {
    List<PokemonResponseDto> findAllPokemons();

    PokemonResponseDto findPokemonById(Integer id);

    List<PokemonResponseDto> findPokemonByTrainerId(Integer id);

    List<PokemonResponseDto> findPokemonByTrainerIdAndSearch(Integer id, SearchPokemonDto search);

    PokemonResponseDto storePokemon(InputPokemonDto pokemonDto);

    PokemonResponseDto updatePokemon(UpdatePokemonDto pokemonDto);

    void deletePokemonById(Integer id);
}
