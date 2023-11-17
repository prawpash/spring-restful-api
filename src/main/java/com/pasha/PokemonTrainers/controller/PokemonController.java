package com.pasha.PokemonTrainers.controller;

import java.util.List;

import com.pasha.PokemonTrainers.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseDto<List<PokemonResponseDto>> getAllPokemon(){
        List<PokemonResponseDto> pokemons = pokemonService.findAllPokemons();
        return ResponseDto.<List<PokemonResponseDto>>builder().data(pokemons).build();
    }

    @GetMapping("/{id}")
    public ResponseDto<PokemonResponseDto> getPokemonById(@PathVariable("id") Integer id){
        PokemonResponseDto pokemon = pokemonService.findPokemonById(id);
        return ResponseDto.<PokemonResponseDto>builder().data(pokemon).build();
    }

    @PostMapping("")
    public ResponseDto<PokemonResponseDto> storePokemon(@Valid @RequestBody InputPokemonDto pokemonDto){
        PokemonResponseDto pokemon = pokemonService.storePokemon(pokemonDto);
        return ResponseDto.<PokemonResponseDto>builder()
                .data(pokemon)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDto<PokemonResponseDto> updatePokemon(@Valid @RequestBody UpdatePokemonDto pokemonDto, @PathVariable("id") Integer id){
        pokemonDto.setId(id);
        PokemonResponseDto pokemon = pokemonService.updatePokemon(pokemonDto);
        return ResponseDto.<PokemonResponseDto>builder()
                .data(pokemon)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deletePokemon(@PathVariable("id") Integer id){
        pokemonService.deletePokemonById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ResponseDto.<String>builder().data("ok").build());
    }

}
