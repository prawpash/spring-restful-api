package com.pasha.PokemonTrainers.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.pasha.PokemonTrainers.dto.UpdateTrainerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.pasha.PokemonTrainers.dto.TrainerDto;
import com.pasha.PokemonTrainers.model.Trainer;
import com.pasha.PokemonTrainers.repository.TrainerRepository;
import com.pasha.PokemonTrainers.service.TrainerService;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    @Autowired
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
    @Transactional(readOnly = true)
    public List<TrainerDto> findAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        return trainers.stream().map(this::mapToTrainerDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerDto findTrainerById(Integer id) {
        Trainer trainer = trainerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer with id: " + id + " is not found"));
        return mapToTrainerDto(trainer);
    }

    @Override
    public Optional<Trainer> findTrainerByUsername(String username) {
        return trainerRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public TrainerDto storeTrainer(TrainerDto trainerDto) {
        Boolean trainerUsernameExists = this.checkIfUsernameExists(trainerDto.getUsername());
        if(trainerUsernameExists){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The trainer with username: " + trainerDto.getUsername() + " is already exists");
        }

        Trainer trainer = trainerRepository.save(mapToTrainer(trainerDto));
        return mapToTrainerDto(trainer);
    }

    @Override
    @Transactional
    public UpdateTrainerDto updateTrainer(UpdateTrainerDto trainerDto) {
        Trainer trainer = trainerRepository.findById(trainerDto.getId())
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Trainer with id: " + trainerDto.getId() + " is not found"
            ));

        if(Objects.nonNull(trainerDto.getName())){
            trainer.setName(trainerDto.getName());
        }

        if(Objects.nonNull(trainerDto.getUsername())){
            if(trainerRepository.findByUsernameExceptId(trainerDto.getUsername(), trainer.getId()).isPresent()){
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "The trainer with username: " + trainerDto.getUsername() + " is already exists"
                );
            }
            trainer.setUsername(trainerDto.getUsername());
        }

        if(Objects.nonNull(trainerDto.getPassword())){
            trainer.setPassword(trainerDto.getPassword());
        }

        trainerRepository.save(trainer);

        return UpdateTrainerDto.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .username(trainer.getUsername())
                .password(trainer.getPassword())
                .build();
    }

    @Override
    @Transactional
    public void deleteTrainerById(Integer id) {
        trainerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer with id: " + id + " is not found"));

        trainerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkIfUsernameExists(String username) {
       Optional<Trainer> trainer = trainerRepository.findByUsername(username);
       return trainer.isPresent();
    }
    
}
