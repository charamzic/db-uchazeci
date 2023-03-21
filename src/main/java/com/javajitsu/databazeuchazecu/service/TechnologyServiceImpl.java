package com.javajitsu.databazeuchazecu.service;

import com.javajitsu.databazeuchazecu.dto.TechnologyRequestDTO;
import com.javajitsu.databazeuchazecu.dto.TechnologyResponseDTO;
import com.javajitsu.databazeuchazecu.repository.TechnologyRepository;
import com.javajitsu.databazeuchazecu.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService{

    private final TechnologyRepository repository;
    private final Mapper mapper;

    @Override
    public Set<TechnologyResponseDTO> findAll() {
        final var allTechnologies = repository.findAll();
        return allTechnologies.stream()
                .map(mapper::mapToTechnologyDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public TechnologyResponseDTO saveTechnology(TechnologyRequestDTO technology) {
        final var technologyEntity = mapper.mapToTechnologyEntity(technology);
        final var savedTechnology = repository.save(technologyEntity);
        return mapper.mapToTechnologyDTO(savedTechnology);
    }

    @Override
    public TechnologyResponseDTO findById(Long id) {
        final var technologyOptional = repository.findById(id);
        return technologyOptional.map(mapper::mapToTechnologyDTO).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateTechnology(TechnologyRequestDTO request, Long technologyId) {
        repository.updateNameById(request.getName(), technologyId);
    }

    @Override
    public boolean technologyExists(String technologyName) {
        return repository.existsByName(technologyName);
    }
}
