package com.pasha.PokemonTrainers.service;

import com.pasha.PokemonTrainers.dto.AuthenticationResponseDto;
import com.pasha.PokemonTrainers.dto.InputAuthenticationDto;
import com.pasha.PokemonTrainers.dto.ResponseDto;

public interface AuthenticationService {

    ResponseDto<AuthenticationResponseDto> authenticate(InputAuthenticationDto authenticationRequest);
}
