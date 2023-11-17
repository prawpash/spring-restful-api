package com.pasha.PokemonTrainers.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicUpdate
@Table(name = "pokemons")
public class Pokemon {

    @Id
    @SequenceGenerator(
        name = "pokemon_id_sequence",
        sequenceName = "pokemon_id_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "pokemon_id_sequence"
    )
    private Integer id;
    private String name;
    private String ability;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne()
    @JoinColumn(name = "trainer_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Trainer trainer;

    public Integer getTrainerId(){
        if(trainer != null){
            return trainer.getId();
        }
        return null;
    }
}
