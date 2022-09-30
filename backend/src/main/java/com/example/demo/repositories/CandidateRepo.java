package com.example.demo.repositories;

import com.example.demo.models.Candidate;
import com.example.demo.models.Liste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate,Long> {
    @Query("select c from Candidate c where c.candidateId=?1")
    Optional<Candidate> findCandidateBy(Long id);

    List<Candidate> findAllByOrderByCandidateVotesDesc();
    //List<Candidate> findAllByOrderByCandidateVotesByDescByListeId(Long id);
    //Candidate findFirstByCandidateVotesOrderByDescOrderByListeId(int liste_id);

//    List<Candidate> findAllByOrderByCandidateVotesDescListe();
//
//    Candidate findFirstByCandidateVotesAndListe(int id);
    @Query("select c from Candidate c where c.candidateVotes=(select MAX(candidateVotes) from Candidate) and c.liste=?1")
    Candidate findPresident(Liste liste);
}
