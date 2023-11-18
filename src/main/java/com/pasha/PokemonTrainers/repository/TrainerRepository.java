package com.pasha.PokemonTrainers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasha.PokemonTrainers.model.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Integer>{
    Optional<Trainer> findByUsername(String username);

    @Query(value = "SELECT * FROM trainers t WHERE t.username = :username AND t.id != :id", nativeQuery = true)
    Optional<Trainer> findByUsernameExceptId(@Param("username") String username,@Param("id") Integer id);
}
