package com.pasha.PokemonTrainers.controller;

import java.util.List;

import com.pasha.PokemonTrainers.dto.ResponseDto;
import com.pasha.PokemonTrainers.dto.UpdateTrainerDto;
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
    public ResponseDto<List<TrainerDto>> getAllTrainer(){
        List<TrainerDto> trainers= trainerService.findAllTrainers();
        return ResponseDto.<List<TrainerDto>>builder().data(trainers).build();
    }

    @GetMapping("/{id}")
    public ResponseDto<TrainerDto> getTrainerById(@PathVariable("id") Integer id){
        TrainerDto trainer = trainerService.findTrainerById(id);
        return ResponseDto.<TrainerDto>builder().data(trainer).build();
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
