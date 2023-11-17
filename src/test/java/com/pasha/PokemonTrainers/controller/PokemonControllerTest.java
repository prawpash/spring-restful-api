package com.pasha.PokemonTrainers.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pasha.PokemonTrainers.dto.*;
import com.pasha.PokemonTrainers.model.Pokemon;
import com.pasha.PokemonTrainers.model.Trainer;
import com.pasha.PokemonTrainers.repository.PokemonRepository;
import com.pasha.PokemonTrainers.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PokemonControllerTest {
    private final String API_ROOT = "/api/v1/pokemons";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        pokemonRepository.deleteAll();
        trainerRepository.deleteAll();
    }

    @Test
    void testCreatePokemonBadRequest() throws Exception {
        InputPokemonDto inputPokemonDto = new InputPokemonDto();

        mockMvc.perform(
                post(API_ROOT)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(inputPokemonDto))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            ResponseDto<InputPokemonDto> pokemon = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(pokemon.getErrors());
        });
    }

    @Test
    void testCreatePokemonTrainerNotFound() throws Exception {
        InputPokemonDto inputPokemonDto = new InputPokemonDto();
        inputPokemonDto.setName("Bulbasaur");
        inputPokemonDto.setAbility("Overgrow");
        inputPokemonDto.setTrainer_id(2);

        mockMvc.perform(
                post(API_ROOT)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(inputPokemonDto))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            ResponseDto<String> pokemon = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
                    });

            assertNotNull(pokemon.getErrors());
        });
    }

    @Test
    void testCreatePokemonSuccess() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("halo");
        trainerRepository.save(trainer);

        InputPokemonDto inputPokemonDto = new InputPokemonDto();
        inputPokemonDto.setName("Bulbasaur");
        inputPokemonDto.setAbility("Overgrow");
        inputPokemonDto.setTrainer_id(trainer.getId());

        mockMvc.perform(
                post(API_ROOT)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(inputPokemonDto))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<PokemonResponseDto> pokemon = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
                    });

            assertNull(pokemon.getErrors());
            assertEquals(inputPokemonDto.getName(), pokemon.getData().getName());
            assertEquals(inputPokemonDto.getAbility(), pokemon.getData().getAbility());
            assertEquals(inputPokemonDto.getTrainer_id(), trainer.getId());
        });
    }

    @Test
    void testGetPokemons() throws Exception{
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("halo");
        trainerRepository.save(trainer);

        Pokemon pokemon = new Pokemon();
        pokemon.setName("Bulbasaur");
        pokemon.setAbility("Overgrow");
        pokemon.setTrainer(trainer);
        pokemonRepository.save(pokemon);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setName("Charmander");
        pokemon2.setAbility("Fire");
        pokemon2.setTrainer(trainer);
        pokemonRepository.save(pokemon2);

        mockMvc.perform(
                get(API_ROOT)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<List<PokemonResponseDto>> pokemonResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNull(pokemonResponse.getErrors());
            assertNotNull(pokemonResponse.getData());
            pokemonResponse.getData().forEach(pokemonResponseDto -> {
                assertNotNull(pokemonResponseDto.getName());
                assertNotNull(pokemonResponseDto.getAbility());
                assertEquals(pokemonResponseDto.getTrainer_id(), trainer.getId());
            });
        });
    }

    @Test
    void testGetPokemonByIdNotFound() throws Exception{
        mockMvc.perform(
                get(API_ROOT + "/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            ResponseDto<String> pokemonResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(pokemonResponse.getErrors());
        });
    }

    @Test
    void testGetPokemonsById() throws Exception{
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("halo");
        trainerRepository.save(trainer);

        Pokemon pokemon = new Pokemon();
        pokemon.setName("Bulbasaur");
        pokemon.setAbility("Overgrow");
        pokemon.setTrainer(trainer);
        pokemonRepository.save(pokemon);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setName("Charmander");
        pokemon2.setAbility("Fire");
        pokemon2.setTrainer(trainer);
        pokemonRepository.save(pokemon2);

        mockMvc.perform(
                get(API_ROOT + "/" + pokemon.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<PokemonResponseDto> pokemonResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNull(pokemonResponse.getErrors());
            assertNotNull(pokemonResponse.getData());
            assertNotNull(pokemonResponse.getData().getName());
            assertNotNull(pokemonResponse.getData().getAbility());
            assertEquals(pokemonResponse.getData().getTrainer_id(), trainer.getId());
        });
    }

    @Test
    void testUpdatePokemonNotFound() throws Exception {
        UpdatePokemonDto updatePokemonDto = new UpdatePokemonDto();
        updatePokemonDto.setName("Charmander");

        mockMvc.perform(
                put(API_ROOT + "/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatePokemonDto))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            ResponseDto<String> pokemon = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
                    });

            assertNotNull(pokemon.getErrors());
        });
    }

    @Test
    void testUpdatePokemonBadRequest() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("halo");
        trainerRepository.save(trainer);

        Pokemon pokemon = new Pokemon();
        pokemon.setName("Bulbasaur");
        pokemon.setAbility("Overgrow");
        pokemon.setTrainer(trainer);
        pokemonRepository.save(pokemon);

        UpdatePokemonDto updatePokemonDto = new UpdatePokemonDto();
        updatePokemonDto.setName("");

        mockMvc.perform(
                put(API_ROOT + "/" + pokemon.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatePokemonDto))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            ResponseDto<Map<String, String>> pokemonResponse = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
                    });

            assertNotNull(pokemonResponse.getErrors());
        });
    }

    @Test
    void testUpdatePokemonTrainerNotFound() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("halo");
        trainerRepository.save(trainer);

        Pokemon pokemon = new Pokemon();
        pokemon.setName("Bulbasaur");
        pokemon.setAbility("Overgrow");
        pokemon.setTrainer(trainer);
        pokemonRepository.save(pokemon);

        UpdatePokemonDto updatePokemonDto = new UpdatePokemonDto();
        updatePokemonDto.setTrainer_id(99);

        mockMvc.perform(
                put(API_ROOT + "/" + pokemon.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatePokemonDto))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            ResponseDto<String> pokemonResponse = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
                    });

            assertNotNull(pokemonResponse.getErrors());
        });
    }

    @Test
    void testUpdatePokemonSuccess() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("halo");
        trainerRepository.save(trainer);

        Pokemon pokemon = new Pokemon();
        pokemon.setName("Bulbasaur");
        pokemon.setAbility("Overgrow");
        pokemon.setTrainer(trainer);
        pokemonRepository.save(pokemon);

        UpdatePokemonDto updatePokemonDto = new UpdatePokemonDto();
        updatePokemonDto.setName("Charmander");
        updatePokemonDto.setAbility("Fire");

        mockMvc.perform(
                put(API_ROOT + "/" + pokemon.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatePokemonDto))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<PokemonResponseDto> pokemonResponse = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
                    });

            assertNull(pokemonResponse.getErrors());
            assertEquals(updatePokemonDto.getName(), pokemonResponse.getData().getName());
            assertEquals(updatePokemonDto.getAbility(), pokemonResponse.getData().getAbility());
            assertEquals(pokemon.getTrainer().getId(), pokemonResponse.getData().getTrainer_id());
        });
    }

    @Test
    void testDeletePokemonSuccess() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("halo");
        trainerRepository.save(trainer);

        Pokemon pokemon = new Pokemon();
        pokemon.setName("Bulbasaur");
        pokemon.setAbility("Overgrow");
        pokemon.setTrainer(trainer);
        pokemonRepository.save(pokemon);

        mockMvc.perform(
                delete(API_ROOT + "/" + pokemon.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isNoContent()
        ).andDo(result -> {
            ResponseDto<String> pokemonResponse = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
                    });

            assertNull(pokemonResponse.getErrors());
            assertEquals("ok", pokemonResponse.getData());
        });
    }

    @Test
    void testDeletePokemonNotFound() throws Exception {
        mockMvc.perform(
                delete(API_ROOT + "/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            ResponseDto<String> pokemonResponse = objectMapper.readValue(
                    result
                            .getResponse()
                            .getContentAsString(), new TypeReference<>() {
                    });

            assertNotNull(pokemonResponse.getErrors());
        });
    }
}