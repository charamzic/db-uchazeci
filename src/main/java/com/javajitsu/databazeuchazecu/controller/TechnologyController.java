package com.javajitsu.databazeuchazecu.controller;

import com.javajitsu.databazeuchazecu.dto.TechnologyRequestDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyResponseDTO;
import com.javajitsu.databazeuchazecu.exception.TechnologyAlreadyExistsException;
import com.javajitsu.databazeuchazecu.service.TechnologyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService technologyService;

    @GetMapping("/technologies")
    public Set<TechnologyResponseDTO> retrieveAllTechnologies() {
        return technologyService.findAll();
    }

    @GetMapping("/technologies/{id}")
    public EntityModel<TechnologyResponseDTO> retrieveTechnology(@PathVariable Long id) {
        final var foundTechnology = technologyService.findById(id);
        if (foundTechnology == null) {
            throw new EntityNotFoundException("Technology with ID: {" + id + "} not found");
        }
        final var entityModel = EntityModel.of(foundTechnology);
        final var link = linkTo(methodOn(this.getClass()).retrieveAllTechnologies());
        entityModel.add(link.withRel("/technologies"));
        return entityModel;
    }

    @DeleteMapping("/technologies/{id}")
    public void deleteTechnology(@PathVariable Long id) {
        technologyService.deleteById(id);
    }

    @PostMapping("/technologies")
    public ResponseEntity<TechnologyResponseDTO> createTechnology(@RequestBody TechnologyRequestDTO request) {
        if (technologyService.technologyExists(request.getName())) {
            throw new TechnologyAlreadyExistsException("Technology {" + request.getName() + "} already exists.");
        }
        final var createdTechnology = technologyService.saveTechnology(request);
        final var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTechnology.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/technologies/{id}")
    public ResponseEntity<TechnologyResponseDTO> updateTechnology(@PathVariable Long id, @RequestBody TechnologyRequestDTO request) {
        final var technologyToUpdate = technologyService.findById(id);
        if (technologyToUpdate == null) {
            throw new EntityNotFoundException("Technology with ID: {" + id + "} not found");
        }
        technologyService.updateTechnology(request, id);
        return ResponseEntity.ok().build();
    }
}
