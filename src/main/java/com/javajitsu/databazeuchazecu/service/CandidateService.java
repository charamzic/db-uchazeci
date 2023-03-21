package com.javajitsu.databazeuchazecu.service;

import com.javajitsu.databazeuchazecu.dto.CandidateDetailRequestDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateResponseDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateDetailResponseDTO;

import java.util.List;

public interface CandidateService {

    List<CandidateResponseDTO> findAll();

    CandidateDetailResponseDTO saveCandidate(CandidateDetailRequestDTO candidate);

    CandidateDetailResponseDTO retrieveCandidateDetail(Long id);

    CandidateDetailResponseDTO findById(Long id);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    void updateCandidate(CandidateDetailRequestDTO candidateDTO, Long candidateId);

    CandidateDetailResponseDTO saveCandidateWithTechnologies(CandidateDetailRequestDTO request);
}
