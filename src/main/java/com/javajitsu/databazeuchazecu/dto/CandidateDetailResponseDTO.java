package com.javajitsu.databazeuchazecu.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class CandidateDetailResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Set<TechnologyDetailResponseDTO> technologies;
}
