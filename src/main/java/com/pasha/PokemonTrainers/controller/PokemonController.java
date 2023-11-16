package com.pasha.PokemonTrainers.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pasha.PokemonTrainers.dto.PokemonDto;
import com.pasha.PokemonTrainers.service.PokemonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/pokemons")
public class PokemonController {
    private final PokemonService pokemonService;
    
    PokemonController(PokemonService pokemonService){
        this.pokemonService = pokemonService;
    }

    @GetMapping("")
    public List<PokemonDto> getAllPokemon(){
        return pokemonService.findAllPokemons();
    }

    @GetMapping("/{id}")
    public PokemonDto getPokemonById(@PathVariable("id") Integer id){
        return pokemonService.findPokemonById(id);
    }

    @PostMapping("")
    public PokemonDto storePokemon(@Valid @RequestBody PokemonDto pokemonDto){
        PokemonDto pokemon = pokemonService.storePokemon(pokemonDto);
        return pokemon;
    }

    @PutMapping("/{id}")
    public PokemonDto updatePokemon(@Valid @RequestBody PokemonDto pokemonDto, @PathVariable("id") Integer id){
        pokemonDto.setId(id);
        PokemonDto pokemon = pokemonService.updatePokemon(pokemonDto);
        return pokemon;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePokemon(@PathVariable("id") Integer id){
        pokemonService.deletePokemonById(id);
        return ResponseEntity.noContent().build();
    }

}
