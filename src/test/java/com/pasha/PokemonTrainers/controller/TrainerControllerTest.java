package com.pasha.PokemonTrainers.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pasha.PokemonTrainers.dto.PokemonResponseDto;
import com.pasha.PokemonTrainers.dto.ResponseDto;
import com.pasha.PokemonTrainers.dto.TrainerDto;
import com.pasha.PokemonTrainers.dto.UpdateTrainerDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TrainerControllerTest {
    private final String API_ROOT="/api/v1/trainers";
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
        trainerRepository.deleteAll();
        pokemonRepository.deleteAll();
    }

    @Test
    void testRegisterBadRequest() throws Exception{
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("");
        trainerDto.setUsername("");
        trainerDto.setPassword("");

        mockMvc.perform(
                post(API_ROOT)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(trainerDto))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            ResponseDto<TrainerDto> trainer = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(trainer.getErrors());
        });
    }

    @Test
    void testRegisterSuccess() throws Exception{
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("Ash");
        trainerDto.setUsername("ash");
        trainerDto.setPassword("passwordAsh");

        mockMvc.perform(
                post(API_ROOT)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(trainerDto))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<TrainerDto> trainer = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
           });

            assertNotNull(trainer.getData().getId());
            assertNotNull(trainer.getData().getName());
            assertNotNull(trainer.getData().getUsername());
        });
    }

    @Test
    void testRegisterDuplicate() throws Exception{
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("passwordAsh");
        trainerRepository.save(trainer);

        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("Ash");
        trainerDto.setUsername("ash");
        trainerDto.setPassword("passwordAsh");

        mockMvc.perform(
                post(API_ROOT)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(trainerDto))
        ).andExpectAll(
                status().isConflict()
        ).andDo(result -> {
            ResponseDto<String> trainersResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(trainersResponse.getErrors());
        });
    }

    @Test
    void getTrainersSuccess() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("passwordAsh");
        trainerRepository.save(trainer);

        mockMvc.perform(
                get(API_ROOT)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<List<TrainerDto>> trainersResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNull(trainersResponse.getErrors());
            assertNotNull(trainersResponse.getData());
        });
    }

    @Test
    void getTrainerById() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("passwordAsh");
        trainerRepository.save(trainer);

        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("Ash");
        trainerDto.setUsername("ash");
        trainerDto.setPassword("passwordAsh");

        mockMvc.perform(
                get(API_ROOT + "/" + trainer.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<TrainerDto> trainerResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNull(trainerResponse.getErrors());
            assertEquals(trainer.getName(), trainerResponse.getData().getName());
            assertEquals(trainer.getUsername(), trainerResponse.getData().getUsername());
        });
    }

    @Test
    void getTrainerByIdNotFound() throws Exception {
        mockMvc.perform(
                get("/api/v1/trainers/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            ResponseDto<String> trainerResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(trainerResponse.getErrors());
        });
    }

    @Test
    void deleteTrainer() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("passwordAsh");
        trainerRepository.save(trainer);

        mockMvc.perform(
                delete(API_ROOT + "/" + trainer.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isNoContent()
        ).andDo(result -> {
            ResponseDto<String> trainerResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNull(trainerResponse.getErrors());
            assertEquals("ok", trainerResponse.getData());
        });
    }

    @Test
    void deleteTrainerNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/v1/trainers/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            ResponseDto<String> trainerResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(trainerResponse.getErrors());
        });
    }

    @Test
    void updateTrainer() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("passwordAsh");
        trainerRepository.save(trainer);

        UpdateTrainerDto trainerDto = new UpdateTrainerDto();
        trainerDto.setName("James");

        mockMvc.perform(
                put(API_ROOT + "/" + trainer.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(trainerDto))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<TrainerDto> trainerResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNull(trainerResponse.getErrors());
            assertEquals(trainer.getUsername(), trainerResponse.getData().getUsername());
            assertEquals(trainerDto.getName(), trainerResponse.getData().getName());
            assertEquals(trainer.getPassword(), trainerResponse.getData().getPassword());
        });
    }

    @Test
    void updateTrainerNotFound() throws Exception {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("James");

        mockMvc.perform(
                put("/api/v1/trainers/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(trainerDto))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            ResponseDto<String> trainerResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(trainerResponse.getErrors());
        });
    }

    @Test
    void updateTrainerDuplicate() throws Exception{
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("passwordAsh");
        trainerRepository.save(trainer);

        Trainer trainer2 = new Trainer();
        trainer2.setName("James");
        trainer2.setUsername("james");
        trainer2.setPassword("passwordJames");
        trainerRepository.save(trainer2);

        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setUsername("ash");

        mockMvc.perform(
                put(API_ROOT + "/" + trainer2.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(trainerDto))
        ).andExpectAll(
                status().isConflict()
        ).andDo(result -> {
            ResponseDto<String> trainersResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(trainersResponse.getErrors());
        });
    }

    @Test
    void getTrainerPokemons() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setUsername("ash");
        trainer.setPassword("passwordAsh");
        trainerRepository.save(trainer);

        Pokemon pokemon = new Pokemon();
        pokemon.setTrainer(trainer);
        pokemon.setAbility("Overgrow");
        pokemon.setName("Bulbasaur");
        pokemonRepository.save(pokemon);

        mockMvc.perform(
                get(API_ROOT + "/" + trainer.getId() + "/pokemons")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            ResponseDto<List<PokemonResponseDto>> trainersResponse = objectMapper
                    .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNull(trainersResponse.getErrors());
            assertNotNull(trainersResponse.getData());
            trainersResponse.getData().forEach(pokemonResponseDto -> {
                assertEquals(pokemonResponseDto.getTrainer_id(), trainer.getId());
                assertNotNull(pokemonResponseDto.getName());
                assertNotNull(pokemonResponseDto.getAbility());
            });
        });
    }
}