package com.pasha.PokemonTrainers.service.impl;

import com.pasha.PokemonTrainers.dto.AuthenticationResponseDto;
import com.pasha.PokemonTrainers.dto.InputAuthenticationDto;
import com.pasha.PokemonTrainers.dto.ResponseDto;
import com.pasha.PokemonTrainers.model.Trainer;
import com.pasha.PokemonTrainers.service.AuthenticationService;
import com.pasha.PokemonTrainers.service.JwtService;
import com.pasha.PokemonTrainers.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TrainerService trainerService;
    private final JwtService jwtService;

    @Override
    public ResponseDto<AuthenticationResponseDto> authenticate(InputAuthenticationDto authenticationRequest) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password is Incorrect");
        }

        Optional<Trainer> trainer = trainerService.findTrainerByUsername(
                authenticationRequest.getUsername()
        );

        if(trainer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password is Incorrect");
        }

        String jwtToken = jwtService.generateToken(trainer.get());
        return ResponseDto.<AuthenticationResponseDto>builder().data(
                AuthenticationResponseDto.builder()
                        .token(jwtToken)
                        .build()
        ).build();
    }
}
