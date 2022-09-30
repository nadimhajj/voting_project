package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.CandidateRepo;
import com.example.demo.repositories.ListeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListeService {
    private final ListeRepo listeRepo;
    private final CandidateRepo candidateRepo;

    @Autowired
    public ListeService(ListeRepo listeRepo, CandidateRepo candidateRepo) {
        this.listeRepo = listeRepo;
        this.candidateRepo = candidateRepo;
    }
    public Optional<Liste> findListe(Long id){
        return listeRepo.findListeBy(id);
    };

    //retrieve all lists
    public List<Liste> findAllListes(){
        return listeRepo.findAll();
    }

    public Optional<Candidate> findCandidate(Long id){
        return candidateRepo.findCandidateBy(id);
    }

    public Liste saveListe(Liste liste){
         return listeRepo.save(liste);
    }

//    public Candidate saveCandidate(Candidate candidate){
//        return candidateRepo.save(candidate);
//    }
    public Candidate saveCandidate(Candidate candidate){
    return candidateRepo.save(candidate);
}

    public List<Candidate> getCandidates(){
        return candidateRepo.findAllByOrderByCandidateVotesDesc();
    }

    public Candidate findFirst(Liste liste){
       return candidateRepo.findPresident(liste);
    }

    public void updateVote(DTO dto){
       Optional<Liste> liste=findListe(dto.getListeId());
       Optional<Candidate> candidate=findCandidate(dto.getCandidateId());
       if(liste.isPresent()){
           liste.get().setVotes(liste.get().getVotes()+1);
           listeRepo.save(liste.get());
       }
       if(candidate.isPresent()){
           candidate.get().setCandidateVotes(candidate.get().getCandidateVotes()+1);
           candidateRepo.save(candidate.get());
       }
    }

//    public List<ResultDTO> calculateResults(){
//        List<ResultDTO> resultDTOList = new ArrayList<ResultDTO>();
//        for (Liste list : listeRepo.findAll()){
//            ResultDTO result = new ResultDTO();
//            result.setListId(list.getListeId());
//            result.setTotalVotes(list.getVotes());
//            result.setCandidates(list.getCandidateListe());
//        }
//
//        return resultDTO;
//    }

}
