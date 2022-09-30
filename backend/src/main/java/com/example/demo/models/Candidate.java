package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    private Long candidateId;
    private String candidateName;
    private Long candidateVotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listeId")
    @JsonBackReference
    private Liste liste;

    public Candidate(Long candidateId, String candidateName, Long candidateVotes, Optional<Liste> liste) {
    }

    public void setListe(Liste liste) {
        this.liste = liste;
    }
}
