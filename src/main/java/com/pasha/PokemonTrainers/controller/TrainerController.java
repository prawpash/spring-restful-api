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

import com.pasha.PokemonTrainers.dto.TrainerDto;
import com.pasha.PokemonTrainers.service.TrainerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    TrainerController(TrainerService trainerService){
        this.trainerService = trainerService;
    }

    @GetMapping("")
    public List<TrainerDto> getAllTrainer(){
        return trainerService.findAllTrainers();
    }

    @GetMapping("/{id}")
    public TrainerDto getTrainerById(@PathVariable("id") Integer id){
        return trainerService.findTrainerById(id);
    }

    @PostMapping("")
    public TrainerDto storeTrainer(@Valid @RequestBody TrainerDto trainerDto){
        TrainerDto trainer = trainerService.storeTrainer(trainerDto);
        return trainer;
    }

    @PutMapping("/{id}")
    public TrainerDto updateTrainer(@Valid @RequestBody TrainerDto trainerDto, @PathVariable("id") Integer id){
        trainerDto.setId(id);
        TrainerDto trainer = trainerService.updateTrainer(trainerDto);
        return trainer;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrainer(@PathVariable("id") Integer id){
        trainerService.deleteTrainerById(id);
        return ResponseEntity.noContent().build();
    }
}
