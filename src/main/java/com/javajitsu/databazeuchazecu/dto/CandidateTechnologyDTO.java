package com.javajitsu.databazeuchazecu.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

@Data
@Accessors(chain = true)
public class CandidateTechnologyDTO {

    private Long id;

    private CandidateDetailResponseDTO candidate;

    private TechnologyResponseDTO technology;

    @Range(min = 1, max = 10, message = "The rating range is 1-10.")
    private Integer rating;

    private String note;
}
