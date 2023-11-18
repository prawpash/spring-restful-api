package com.pasha.PokemonTrainers.controller;

import com.pasha.PokemonTrainers.dto.*;
import com.pasha.PokemonTrainers.service.AuthenticationService;
import com.pasha.PokemonTrainers.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TrainerService trainerService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<TrainerResponseDto>> register(@Valid @RequestBody InputTrainerDto trainerDto){
        TrainerResponseDto trainer = trainerService.storeTrainer(trainerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.<TrainerResponseDto>builder().data(trainer).build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<AuthenticationResponseDto>> login(@Valid @RequestBody InputAuthenticationDto authRequest){
        ResponseDto<AuthenticationResponseDto> authenticationResponse = authenticationService.authenticate(authRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }
}
