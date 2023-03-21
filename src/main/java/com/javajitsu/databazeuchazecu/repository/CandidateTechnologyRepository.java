package com.javajitsu.databazeuchazecu.repository;

import com.javajitsu.databazeuchazecu.model.CandidateTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateTechnologyRepository extends JpaRepository<CandidateTechnology, Long> {
    boolean existsByTechnology_IdAndCandidate_Id(Long technologyId, Long candidateId);
}
