package com.javajitsu.databazeuchazecu.repository;

import com.javajitsu.databazeuchazecu.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Transactional
    @Modifying
    @Query("update candidate c set c.name = ?1, c.email = ?2 where c.id = ?3")
    int updateNameAndEmailById(String name, String email, Long id);

    @Query("SELECT c FROM candidate c " +
            "LEFT JOIN FETCH c.candidateTechnologies ct " +
            "LEFT JOIN FETCH ct.technology " +
            "WHERE c.id = :id")
    Optional<Candidate> findCandidateByIdWithTechnologies(Long id);

    boolean existsByEmail(String email);
}
