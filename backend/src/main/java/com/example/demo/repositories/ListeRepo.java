package com.example.demo.repositories;

import com.example.demo.models.Liste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListeRepo extends JpaRepository<Liste,Long> {
    @Query("select l from Liste l where l.listeId= ?1")
    Optional<Liste> findListeBy(Long id);
    List<Liste> findAll();

}
