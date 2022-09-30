package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Liste {
    @Id
    private Long listeId;
    private Long votes;
    private static Long total_Votes;
    private double number_of_seats;

    public double getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(double number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    public static Long getTotal_Votes() {
        return total_Votes;
    }

    @OneToMany(mappedBy = "liste",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    @Column(name = "CandidateListe")
    @JsonManagedReference
    private List<Candidate> candidateListe;
}
