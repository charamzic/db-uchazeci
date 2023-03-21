package com.javajitsu.databazeuchazecu.repository;

import com.javajitsu.databazeuchazecu.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    boolean existsByName(String name);
    @Transactional
    @Modifying
    @Query("update technology t set t.name = ?1 where t.id = ?2")
    int updateNameById(String name, Long id);

    Optional<Technology> findByName(String name);
}
