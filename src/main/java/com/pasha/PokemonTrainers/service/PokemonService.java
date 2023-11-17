package com.pasha.PokemonTrainers.service;

import java.util.List;

import com.pasha.PokemonTrainers.dto.InputPokemonDto;
import com.pasha.PokemonTrainers.dto.PokemonDto;
import com.pasha.PokemonTrainers.dto.PokemonResponseDto;
import com.pasha.PokemonTrainers.dto.UpdatePokemonDto;

public interface PokemonService {
    List<PokemonResponseDto> findAllPokemons();

    PokemonResponseDto findPokemonById(Integer id);

    PokemonResponseDto storePokemon(InputPokemonDto pokemonDto);

    PokemonResponseDto updatePokemon(UpdatePokemonDto pokemonDto);

    void deletePokemonById(Integer id);
}
