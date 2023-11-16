package com.pasha.PokemonTrainers.Exception;

import java.util.HashMap;
import java.util.Map;

import com.pasha.PokemonTrainers.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleValidationExceptions(
        MethodArgumentNotValidException ex
    ) {
          Map<String, String> errors = new HashMap<>();
          ex.getBindingResult().getAllErrors().forEach((error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
          });
          return ResponseEntity
                  .status(ex.getStatusCode())
                  .body(
                          ResponseDto
                                  .<Map<String, String>>builder()
                                  .errors(errors)
                                  .build()
                  );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseDto<String>> apiException(ResponseStatusException ex){
        return ResponseEntity
                .status(ex.getStatusCode()).body(ResponseDto.<String>builder().errors(ex.getReason()).build());
    }
}
