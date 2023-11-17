package com.pasha.PokemonTrainers.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.pasha.PokemonTrainers.dto.InputPokemonDto;
import com.pasha.PokemonTrainers.dto.PokemonResponseDto;
import com.pasha.PokemonTrainers.dto.UpdatePokemonDto;
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

    private PokemonResponseDto mapToPokemonResponse(Pokemon pokemon){
        return PokemonResponseDto.builder()
                .id(pokemon.getId())
                .name(pokemon.getName())
                .ability(pokemon.getAbility())
                .createdAt(pokemon.getCreatedAt())
                .updatedAt(pokemon.getUpdatedAt())
                .trainer_id(pokemon.getTrainer().getId())
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
    public List<PokemonResponseDto> findAllPokemons() {
        List<Pokemon> pokemons = pokemonRepository.findAll();
        return pokemons.stream().map(this::mapToPokemonResponse).collect(Collectors.toList());
    }

    @Override
    public PokemonResponseDto storePokemon(InputPokemonDto pokemonDto) {
        Trainer trainer = trainerRepository.findById(pokemonDto.getTrainer_id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer with id: " + pokemonDto.getTrainer_id() + " is not found"));

        Pokemon mapToPokemon = Pokemon.builder()
                .name(pokemonDto.getName())
                .ability(pokemonDto.getAbility())
                .trainer(trainer)
                .build();

        Pokemon pokemon = pokemonRepository.save(mapToPokemon);

        return mapToPokemonResponse(pokemon);
    }

    @Override
    public PokemonResponseDto findPokemonById(Integer id) {
       Pokemon pokemon = pokemonRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon with id: " + id + " is not found"));
       
       return mapToPokemonResponse(pokemon);
    }

    @Override
    public PokemonResponseDto updatePokemon(UpdatePokemonDto pokemonDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonDto.getId())
                .orElseThrow(() ->new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Pokemon with id: " + pokemonDto.getId() + " is not found"));

        if(Objects.nonNull(pokemonDto.getName())){
            pokemon.setName(pokemonDto.getName());
        }

        if(Objects.nonNull(pokemonDto.getAbility())){
            pokemon.setAbility(pokemonDto.getAbility());
        }

        if(Objects.nonNull(pokemonDto.getTrainer_id())){
            Trainer trainer = trainerRepository.findById(pokemonDto.getTrainer_id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer with id: " + pokemonDto.getTrainer_id() + " is not found"));

            pokemon.setTrainer(trainer);
        }

        pokemonRepository.save(pokemon);
        return mapToPokemonResponse(pokemon);
    }

    @Override
    public void deletePokemonById(Integer id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pokemon with id: " + id + " is not found"
                ));
        pokemonRepository.deleteById(id);
    }
}
