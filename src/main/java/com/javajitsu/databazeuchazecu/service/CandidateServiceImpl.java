package com.javajitsu.databazeuchazecu.service;

import com.javajitsu.databazeuchazecu.dto.CandidateDetailRequestDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateDetailResponseDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateResponseDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyDetailRequestDTO;
import com.javajitsu.databazeuchazecu.model.Candidate;
import com.javajitsu.databazeuchazecu.model.CandidateTechnology;
import com.javajitsu.databazeuchazecu.model.Technology;
import com.javajitsu.databazeuchazecu.repository.CandidateRepository;
import com.javajitsu.databazeuchazecu.repository.CandidateTechnologyRepository;
import com.javajitsu.databazeuchazecu.repository.TechnologyRepository;
import com.javajitsu.databazeuchazecu.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final TechnologyRepository technologyRepository;
    private final CandidateTechnologyRepository candidateTechnologyRepository;
    private final Mapper mapper;

    @Override
    public List<CandidateResponseDTO> findAll() {
        final var allCandidates = candidateRepository.findAll();
        return allCandidates.stream()
                .map(mapper::mapToCandidateDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public CandidateDetailResponseDTO saveCandidate(CandidateDetailRequestDTO candidate) {
        final var candidateEntity = mapper.mapToCandidateEntity(candidate);
        final var savedCandidate = candidateRepository.save(candidateEntity);
        return mapper.mapToCandidateDetailDTO(savedCandidate);
    }

    @Override
    public CandidateDetailResponseDTO saveCandidateWithTechnologies(CandidateDetailRequestDTO request) {
        final var candidate = new Candidate()
                .setName(request.getName())
                .setEmail(request.getEmail());
        final var candidateTechnologies = new HashSet<CandidateTechnology>();

        request.getTechnologies().forEach(this::saveOrUpdateTechnology);
        request.getTechnologies().forEach(twd -> setCandidateTechnologyConnection(candidate, candidateTechnologies, twd));
        candidate.setCandidateTechnologies(candidateTechnologies);
        return mapper.mapToCandidateDetailDTO(candidateRepository.save(candidate));
    }

    private void setCandidateTechnologyConnection(Candidate candidate,
                                                  HashSet<CandidateTechnology> candidateTechnologies,
                                                  TechnologyDetailRequestDTO twd) {
        var technology = technologyRepository.findByName(twd.getName());
        if (technology.isPresent()
                && !candidateTechnologyRepository.existsByTechnology_IdAndCandidate_Id(technology.get().getId(), candidate.getId())) {
            var candidateTechnology = candidateTechnologyRepository.save(
                            new CandidateTechnology()
                                    .setCandidate(candidate)).setTechnology(technology.get())
                    .setNote(twd.getNote())
                    .setRating(twd.getRating());
            candidateTechnologies.add(candidateTechnology);
        }
    }

    private void saveOrUpdateTechnology(TechnologyDetailRequestDTO twd) {
        var technology = technologyRepository.findByName(twd.getName());
        if (technology.isPresent()) {
            technologyRepository.updateNameById(twd.getName(), technology.get().getId());
        } else {
            technologyRepository.save(new Technology().setName(twd.getName()));
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return candidateRepository.existsByEmail(email);
    }

    @Override
    public void updateCandidate(CandidateDetailRequestDTO request, Long candidateId) {
        var candidate = candidateRepository.findById(candidateId).get();
        if (request.getTechnologies() == null) {
            candidateRepository.updateNameAndEmailById(
                    request.getName(), request.getEmail(), candidateId
            );
            return;
        }
        final var candidateTechnologies = new HashSet<CandidateTechnology>();
        request.getTechnologies().forEach(this::saveOrUpdateTechnology);
        request.getTechnologies().forEach(twd -> setCandidateTechnologyConnection(candidate, candidateTechnologies, twd));
        candidate.setName(request.getName()).setEmail(request.getEmail());
        candidateRepository.save(candidate);
    }

    @Override
    public CandidateDetailResponseDTO retrieveCandidateDetail(Long id) {
        final var foundCandidate = candidateRepository.findCandidateByIdWithTechnologies(id);
        return foundCandidate.map(candidate -> mapper.mapToCandidateDetailDTO(candidate)
                        .setTechnologies(mapper.mapToTechnologyWithDetailDTOs(candidate.getCandidateTechnologies())))
                .orElse(null);
    }

    @Override
    public CandidateDetailResponseDTO findById(Long id) {
        final var foundCandidate = candidateRepository.findById(id);
        return foundCandidate.map(mapper::mapToCandidateDetailDTO).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        candidateRepository.deleteById(id);
    }
}
