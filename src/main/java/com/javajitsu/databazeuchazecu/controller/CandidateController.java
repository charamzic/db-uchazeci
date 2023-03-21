package com.javajitsu.databazeuchazecu.controller;

import com.javajitsu.databazeuchazecu.dto.CandidateDetailRequestDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateResponseDTO;
import com.javajitsu.databazeuchazecu.dto.CandidateDetailResponseDTO;
import com.javajitsu.databazeuchazecu.exception.CandidateAlreadyExistsException;
import com.javajitsu.databazeuchazecu.exception.CandidateNotFoundException;
import com.javajitsu.databazeuchazecu.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/candidates")
    public List<CandidateResponseDTO> retrieveAllCandidates() {
        return candidateService.findAll();
    }

    @GetMapping("/candidates/{id}")
    public EntityModel<CandidateDetailResponseDTO> retrieveCandidate(@PathVariable Long id) {
        final var foundCandidate = candidateService.retrieveCandidateDetail(id);
        if (foundCandidate == null) {
            throw new CandidateNotFoundException("ID: " + id);
        }
        final var entityModel = EntityModel.of(foundCandidate);
        final var link = linkTo(methodOn(this.getClass()).retrieveAllCandidates());
        entityModel.add(link.withRel("/candidates"));
        return entityModel;
    }

    @DeleteMapping("/candidates/{id}")
    public void deleteCandidate(@PathVariable Long id) {
        candidateService.deleteById(id);
    }

    @PostMapping("/candidates")
    public ResponseEntity<CandidateDetailResponseDTO> createCandidate(@Valid @RequestBody CandidateDetailRequestDTO request) {
        if (candidateService.existsByEmail(request.getEmail())) {
            throw new CandidateAlreadyExistsException("Candidate with the email: {" + request.getEmail() + "} already exists in the database.");
        }
        var createdCandidate = new CandidateDetailResponseDTO();
        if (request.getTechnologies() == null) {
            createdCandidate = candidateService.saveCandidate(request);
        } else {
            createdCandidate = candidateService.saveCandidateWithTechnologies(request);
        }
        final var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCandidate.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/candidates/{id}")
    public ResponseEntity<CandidateDetailResponseDTO> updateCandidate(@PathVariable Long id, @RequestBody CandidateDetailRequestDTO request) {
        var candidateToUpdate = candidateService.findById(id);
        if (candidateToUpdate == null) {
            throw new CandidateNotFoundException("Candidate with ID: {" + id + "} not found");
        }
        candidateService.updateCandidate(request, id);
        return ResponseEntity.ok().build();
    }
}
