package com.javajitsu.databazeuchazecu.utils;

import com.javajitsu.databazeuchazecu.dto.CandidateDetailRequestDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateResponseDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateDetailResponseDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyRequestDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyResponseDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyDetailResponseDTO;
import com.javajitsu.databazeuchazecu.model.Candidate;
import com.javajitsu.databazeuchazecu.model.CandidateTechnology;
import com.javajitsu.databazeuchazecu.model.Technology;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Mapper {

    public CandidateDetailResponseDTO mapToCandidateDetailDTO(Candidate candidate) {
        return new CandidateDetailResponseDTO()
                .setId(candidate.getId())
                .setName(candidate.getName())
                .setEmail(candidate.getEmail());
    }

    public CandidateResponseDTO mapToCandidateDTO(Candidate candidate) {
        return new CandidateResponseDTO()
                .setId(candidate.getId())
                .setName(candidate.getName())
                .setEmail(candidate.getEmail());
    }

    public Candidate mapToCandidateEntity(CandidateDetailRequestDTO candidateDto) {
        return new Candidate()
                .setName(candidateDto.getName())
                .setEmail(candidateDto.getEmail());
    }

    public TechnologyResponseDTO mapToTechnologyDTO(Technology technology) {
        return new TechnologyResponseDTO()
                .setId(technology.getId())
                .setName(technology.getName());
    }

    public Technology mapToTechnologyEntity(TechnologyRequestDTO technologyDTO) {
        return new Technology()
                .setName(technologyDTO.getName());
    }

    public Set<TechnologyDetailResponseDTO> mapToTechnologyWithDetailDTOs(Set<CandidateTechnology> candidateTechnologySet) {
        return candidateTechnologySet.stream().map(ct -> new TechnologyDetailResponseDTO()
                        .setId(ct.getTechnology().getId())
                        .setName(ct.getTechnology().getName())
                        .setRating(ct.getRating()).setNote(ct.getNote()))
                .collect(Collectors.toSet());
    }
}
