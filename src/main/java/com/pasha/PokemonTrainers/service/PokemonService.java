package com.pasha.PokemonTrainers.service;

import java.util.List;

import com.pasha.PokemonTrainers.dto.PokemonDto;

public interface PokemonService {
    List<PokemonDto> findAllPokemons();

    PokemonDto findPokemonById(Integer id);

    PokemonDto storePokemon(PokemonDto pokemonDto);

    PokemonDto updatePokemon(PokemonDto pokemonDto);

    void deletePokemonById(Integer id);
}
