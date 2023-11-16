package com.pasha.PokemonTrainers.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pasha.PokemonTrainers.dto.TrainerDto;
import com.pasha.PokemonTrainers.model.Trainer;
import com.pasha.PokemonTrainers.repository.TrainerRepository;
import com.pasha.PokemonTrainers.service.TrainerService;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    TrainerServiceImpl(TrainerRepository trainerRepository){
        this.trainerRepository = trainerRepository;
    }

    private TrainerDto mapToTrainerDto(Trainer trainer){
        return TrainerDto.builder()
            .id(trainer.getId())
            .name(trainer.getName())
            .username(trainer.getUsername())
            .password(trainer.getPassword())
            .createdAt(trainer.getCreatedAt())
            .updatedAt(trainer.getUpdatedAt())
            .build();
    }

    private Trainer mapToTrainer(TrainerDto trainerDto){
        return Trainer.builder()
            .id(trainerDto.getId())
            .name(trainerDto.getName())
            .username(trainerDto.getUsername())
            .password(trainerDto.getPassword())
            .createdAt(trainerDto.getCreatedAt())
            .updatedAt(trainerDto.getUpdatedAt())
            .build();
    }

    @Override
    public List<TrainerDto> findAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        return trainers.stream().map(this::mapToTrainerDto).collect(Collectors.toList());
    }

    @Override
    public TrainerDto findTrainerById(Integer id) {
        Trainer trainer = trainerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer with id: " + id + " is not found"));
        return mapToTrainerDto(trainer);
    }

    @Override
    public TrainerDto storeTrainer(TrainerDto trainerDto) {
        Boolean trainerUsernameExists = this.checkIfUsernameExists(trainerDto.getUsername());
        if(trainerUsernameExists){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The trainer with username: " + trainerDto.getUsername() + " is already exists");
        }

        Trainer trainer = trainerRepository.save(mapToTrainer(trainerDto));
        return mapToTrainerDto(trainer);
    }

    @Override
    public TrainerDto updateTrainer(TrainerDto trainerDto) {
        trainerRepository.findById(trainerDto.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer with id: " + trainerDto.getId() + " is not found"));

        Trainer trainer = trainerRepository.save(mapToTrainer(trainerDto));
        return mapToTrainerDto(trainer);
    }

    @Override
    public void deleteTrainerById(Integer id) {
        trainerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer with id: " + id + " is not found"));

        trainerRepository.deleteById(id);
    }

    @Override
    public Boolean checkIfUsernameExists(String username) {
       Trainer trainer = trainerRepository.findByUsername(username);
       if(trainer != null){
        return true;
       }else{
        return false;
       }
    }
    
}
