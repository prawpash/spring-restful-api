package com.pasha.PokemonTrainers.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pasha.PokemonTrainers.dto.PokemonDto;
import com.pasha.PokemonTrainers.model.Pokemon;
import com.pasha.PokemonTrainers.model.Trainer;
import com.pasha.PokemonTrainers.repository.PokemonRepository;
import com.pasha.PokemonTrainers.repository.TrainerRepository;
import com.pasha.PokemonTrainers.service.PokemonService;

@Service
public class PokemonServiceImpl implements PokemonService{
    private final PokemonRepository pokemonRepository;
    private final TrainerRepository trainerRepository;

    PokemonServiceImpl(PokemonRepository pokemonRepository, TrainerRepository trainerRepository){
        this.pokemonRepository = pokemonRepository;
        this.trainerRepository = trainerRepository;
    }

    private PokemonDto mapToPokemonDto(Pokemon pokemon){
        return PokemonDto.builder()
            .id(pokemon.getId())
            .name(pokemon.getName())
            .ability(pokemon.getAbility())
            .createdAt(pokemon.getCreatedAt())
            .updatedAt(pokemon.getUpdatedAt())
            // .trainer_id(pokemon.getTrainerId())
            .trainer(pokemon.getTrainer())
            .build();
    }

    private Pokemon mapToPokemon(PokemonDto pokemonDto){
        return Pokemon.builder()
            .id(pokemonDto.getId())
            .name(pokemonDto.getName())
            .ability(pokemonDto.getAbility())
            .createdAt(pokemonDto.getCreatedAt())
            .updatedAt(pokemonDto.getUpdatedAt())
            .trainer(pokemonDto.getTrainer())
            .build();
    }

    @Override
    public List<PokemonDto> findAllPokemons() {
        List<Pokemon> pokemons = pokemonRepository.findAll();
        return pokemons.stream().map(this::mapToPokemonDto).collect(Collectors.toList());
    }

    @Override
    public PokemonDto storePokemon(PokemonDto pokemonDto) {
        Trainer trainer = trainerRepository.findById(pokemonDto.getTrainer_id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer with id: " + pokemonDto.getTrainer_id() + " is not found"));
        
        pokemonDto.setTrainer(trainer);
        Pokemon pokemon = pokemonRepository.save(mapToPokemon(pokemonDto));
        return mapToPokemonDto(pokemon);
    }

    @Override
    public PokemonDto findPokemonById(Integer id) {
       Pokemon pokemon = pokemonRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon with id: " + id + " is not found"));
       
       return mapToPokemonDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = pokemonRepository.save(mapToPokemon(pokemonDto));
        return mapToPokemonDto(pokemon);
    }

    @Override
    public void deletePokemonById(Integer id) {
        pokemonRepository.deleteById(id);
    }
}
