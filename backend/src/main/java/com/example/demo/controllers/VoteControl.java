package com.example.demo.controllers;

import com.example.demo.models.Candidate;
import com.example.demo.models.CandidateObject;
import com.example.demo.models.DTO;
import com.example.demo.models.Liste;
import com.example.demo.services.ListeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.lang.Math.floor;

@RestController
@RequestMapping("/vote")
public class VoteControl {
    private final ListeService listeService;

    @Autowired
    public VoteControl(ListeService listeService) {
        this.listeService = listeService;
    }
    @PostMapping("/add/liste")
    public ResponseEntity<Liste> addListe(@RequestBody Liste liste){
        Liste newListe=listeService.saveListe(liste);
        return new ResponseEntity<Liste>(newListe, HttpStatus.CREATED);
    }

    @GetMapping("/get/{listeId}")
    public ResponseEntity<Liste>getListe(@PathVariable Long listeId){
        Liste liste = listeService.findListe(listeId).get();
        return new ResponseEntity<Liste>(liste, HttpStatus.OK);
    }
    public HashMap<Long,Double> sortHashMap(HashMap<Long, Double> hashmap){
        HashMap<Long, Double> map = new HashMap<>();
        LinkedHashMap<Long, Double> sortedMap = new LinkedHashMap<>();
        ArrayList<Double> list = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        for (double num : list) {
            for (Map.Entry<Long, Double> entry : map.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        return sortedMap;
    }
    //@GetMapping("get/{listeid}")
//    public ResponseEntity<List<Candidate>> getCandidatesByListeId(@PathVariable Long listeid){
//        List<Candidate> candidateList=listeService.getAllCandidatesByList(listeid);
//        return new ResponseEntity<>(candidateList,HttpStatus.OK);
//    }
    @GetMapping("get/results")
    public ResponseEntity<Candidate> getResults() {
        List<Liste> list = listeService.findAllListes(); //get lists
        double number_of_seats = 11;
        double number_of_votes = 30;
        int number_of_seats_reste = (int) number_of_seats;
        double EQ = number_of_votes / number_of_seats;
        System.out.println("EQ: " + EQ);
        ArrayList<Double> primarySeats = new ArrayList<>();
        ArrayList<Double> listeRests = new ArrayList<>();
        for (Liste l : list) {
            System.out.println("Votes of list " + l.getListeId() + " " + l.getVotes());
            double firstListSeats = floor(l.getVotes() / EQ);
            System.out.println("Seats of list " + l.getListeId() + " " + firstListSeats);
            double reste = l.getVotes() - firstListSeats * EQ;
            number_of_seats_reste -= firstListSeats;
            primarySeats.add(firstListSeats);
            listeRests.add(reste);
        }
        double winning_list = Collections.max(primarySeats);
        int winning_index = primarySeats.indexOf(winning_list);
        Candidate president = listeService.findFirst(listeService.findListe((long) (winning_index + 1)).get());

        return new ResponseEntity<>(president, HttpStatus.OK);
    }


    @PostMapping("/add/candidate")
    public ResponseEntity<Candidate> addCandidate(@RequestBody CandidateObject candidateObject){
        Candidate newCandidate=new Candidate(); //best to use setters
         newCandidate.setCandidateId(candidateObject.getCandidateId());
        newCandidate.setCandidateName(candidateObject.getCandidateName());
        newCandidate.setCandidateVotes(candidateObject.getCandidateVotes());
        Liste liste = listeService.findListe(candidateObject.getListeId()).get();
        newCandidate.setListe(liste);
        newCandidate=listeService.saveCandidate(newCandidate);
        return new ResponseEntity<Candidate>(newCandidate, HttpStatus.CREATED); //for development purposes, best to return the result with the status
    }

    @PutMapping("/update")
    public ResponseEntity vote( @RequestBody DTO dto){
        listeService.updateVote(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
