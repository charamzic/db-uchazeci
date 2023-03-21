package com.javajitsu.databazeuchazecu.service;

import com.javajitsu.databazeuchazecu.dto.TechnologyRequestDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyResponseDTO;

import java.util.Set;

public interface TechnologyService {

    Set<TechnologyResponseDTO> findAll();

    TechnologyResponseDTO saveTechnology(TechnologyRequestDTO technology);

    TechnologyResponseDTO findById(Long id);

    void deleteById(Long id);

    void updateTechnology(TechnologyRequestDTO candidateDTO, Long technologyId);

    boolean technologyExists(String technologyName);
}
