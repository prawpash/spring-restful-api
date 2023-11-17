package com.pasha.PokemonTrainers.controller;

import java.util.List;

import com.pasha.PokemonTrainers.dto.*;
import com.pasha.PokemonTrainers.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pasha.PokemonTrainers.service.TrainerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/trainers")
public class TrainerController {
    private final TrainerService trainerService;
    private final PokemonService pokemonService;

    TrainerController(TrainerService trainerService, PokemonService pokemonService){
        this.trainerService = trainerService;
        this.pokemonService = pokemonService;
    }

    @GetMapping("")
    public ResponseDto<List<TrainerDto>> getAllTrainer(){
        List<TrainerDto> trainers= trainerService.findAllTrainers();
        return ResponseDto.<List<TrainerDto>>builder().data(trainers).build();
    }

    @GetMapping("/{id}")
    public ResponseDto<TrainerDto> getTrainerById(@PathVariable("id") Integer id){
        TrainerDto trainer = trainerService.findTrainerById(id);
        return ResponseDto.<TrainerDto>builder().data(trainer).build();
    }

    @GetMapping("/{id}/pokemons")
    public ResponseDto<List<PokemonResponseDto>> getTrainerPokemons(
            @PathVariable("id") Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "ability", required = false) String ability,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ){
        SearchPokemonDto searchPokemonDto = SearchPokemonDto.builder()
                .name(name)
                .ability(ability)
                .page(page)
                .size(size)
                .build();
        List<PokemonResponseDto> pokemons = pokemonService.findPokemonByTrainerIdAndSearch(id, searchPokemonDto);
        return ResponseDto.<List<PokemonResponseDto>>builder().data(pokemons).build();
    }

    @PostMapping("")
    public ResponseDto<TrainerDto> storeTrainer(@Valid @RequestBody TrainerDto trainerDto){
        TrainerDto trainer = trainerService.storeTrainer(trainerDto);
        return ResponseDto.<TrainerDto>builder().data(trainer).build();
    }

    @PutMapping("/{id}")
    public ResponseDto<UpdateTrainerDto> updateTrainer(@Valid @RequestBody UpdateTrainerDto trainerDto, @PathVariable("id") Integer id){
        trainerDto.setId(id);
        return ResponseDto.<UpdateTrainerDto>builder().data(trainerService.updateTrainer(trainerDto)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteTrainer(@PathVariable("id") Integer id){
        trainerService.deleteTrainerById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).body(ResponseDto.<String>builder().data("ok").build());
    }
}
