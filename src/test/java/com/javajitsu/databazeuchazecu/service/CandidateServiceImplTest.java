package com.javajitsu.databazeuchazecu.service;

import com.javajitsu.databazeuchazecu.dto.CandidateDetailRequestDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateDetailResponseDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateResponseDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyDetailRequestDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyDetailResponseDTO;
import com.javajitsu.databazeuchazecu.model.Candidate;
import com.javajitsu.databazeuchazecu.repository.CandidateRepository;
import com.javajitsu.databazeuchazecu.repository.CandidateTechnologyRepository;
import com.javajitsu.databazeuchazecu.repository.TechnologyRepository;
import com.javajitsu.databazeuchazecu.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class CandidateServiceImplTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private CandidateTechnologyRepository candidateTechnologyRepository;

    @Mock
    private Mapper mapper;

    private CandidateServiceImpl tested;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tested = new CandidateServiceImpl(
                candidateRepository,
                technologyRepository,
                candidateTechnologyRepository,
                mapper
        );
    }

    @Test
    void testFindAll() {
        // given
        Candidate candidate = new Candidate()
                .setId(1L)
                .setName("Vlad Testovič")
                .setEmail("vlad@testovic.ro");
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate);
        when(candidateRepository.findAll()).thenReturn(candidates);
        CandidateResponseDTO candidateResponseDTO = new CandidateResponseDTO()
                .setId(1L)
                .setName("Vlad Testovič")
                .setEmail("testovic@testan.ro");
        when(mapper.mapToCandidateDTO(any())).thenReturn(candidateResponseDTO);

        // when
        List<CandidateResponseDTO> allCandidates = tested.findAll();

        // then
        assertEquals(1, allCandidates.size());
        assertEquals(candidateResponseDTO, allCandidates.get(0));
    }

    @Test
    void testSaveCandidate() {
        // given
        CandidateDetailRequestDTO request = new CandidateDetailRequestDTO()
                .setName("Testoslav Testan")
                .setEmail("testoslav@testan.bg");
        Candidate candidate = new Candidate()
                .setName("Testoslav Testan")
                .setEmail("testoslav@testan.bg");
        Candidate savedCandidate = new Candidate()
                .setId(1L)
                .setName("Testoslav Testan")
                .setEmail("testoslav@testan.bg");
        CandidateDetailResponseDTO expectedResponse = new CandidateDetailResponseDTO();
        when(mapper.mapToCandidateEntity(request)).thenReturn(candidate);
        when(candidateRepository.save(candidate)).thenReturn(savedCandidate);
        when(mapper.mapToCandidateDetailDTO(savedCandidate)).thenReturn(expectedResponse);

        // when
        CandidateDetailResponseDTO actualResponse = tested.saveCandidate(request);

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testSaveCandidateWithTechnologies() {
        // given
        CandidateDetailRequestDTO request = new CandidateDetailRequestDTO()
                .setName("Testoslav Testan")
                .setEmail("testoslav@testan.bg")
                .setTechnologies(Set.of(
                        new TechnologyDetailRequestDTO()
                                .setName("Rust")
                                .setRating(2)
                                .setNote("Koroduje, vy tupci.")
                ));
        CandidateDetailResponseDTO detailResponse = new CandidateDetailResponseDTO()
                .setName("Testoslav Testan")
                .setEmail("testoslav@testan.bg")
                .setTechnologies(Set.of(
                        new TechnologyDetailResponseDTO()
                                .setName("Rust")
                                .setRating(2)
                                .setNote("Koroduje, vy tupci.")
                ));
        when(candidateRepository.save(any(Candidate.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(mapper.mapToCandidateDetailDTO(any())).thenReturn(detailResponse);

        // when
        CandidateDetailResponseDTO response = tested.saveCandidateWithTechnologies(request);

        // then
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getTechnologies().size(), response.getTechnologies().size());
        TechnologyDetailResponseDTO savedTechnology = response.getTechnologies().iterator().next();
        assertEquals("Rust", savedTechnology.getName());
        assertEquals(2, savedTechnology.getRating());
        assertEquals("Koroduje, vy tupci.", savedTechnology.getNote());
    }

    @Test
    void updateCandidate_updatesCandidateNameAndEmail_whenTechnologiesIsNull() {
        // given
        var candidateId = 1L;
        var request = new CandidateDetailRequestDTO()
                .setName("Abraham Testcon")
                .setEmail("testmebaby@onemoretime.com")
                .setTechnologies(null);
        var candidate = new Candidate()
                .setName("Abraham Testcon")
                .setEmail("testmebaby@onemoretime.com");
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        // when
        tested.updateCandidate(request, candidateId);

        // assert
        Mockito.verify(candidateRepository, times(1)).updateNameAndEmailById(request.getName(), request.getEmail(), candidateId);
        Mockito.verify(candidateRepository, times(0)).save(any(Candidate.class));
    }

    @Test
    void updateCandidate_updatesCandidate_whenTechnologiesIsNotNull() {
        // given
        var candidateId = 1L;
        var request = new CandidateDetailRequestDTO()
                .setName("Testomil Testoff")
                .setEmail("testof@mynerves.hu")
                .setTechnologies(
                        Set.of(new TechnologyDetailRequestDTO()
                                .setName("GoLand")
                                .setRating(2)
                                .setNote("Go Jenny, go!"))
                );
        var candidate = new Candidate()
                .setName("Testomil Testoff")
                .setEmail("testof@mynerves.hu");
        ;
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        // when
        tested.updateCandidate(request, candidateId);

        // assert
        Mockito.verify(candidateRepository, times(1)).save(candidate);
    }

}
