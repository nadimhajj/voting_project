package com.example.demo.models;

public class DTO {
    private Long listeId;
    private Long candidateId;

    public DTO(Long listeId, Long candidateId) {
        this.listeId = listeId;
        this.candidateId = candidateId;
    }

    public Long getListeId() {
        return listeId;
    }

    public Long getCandidateId() {
        return candidateId;
    }
}
