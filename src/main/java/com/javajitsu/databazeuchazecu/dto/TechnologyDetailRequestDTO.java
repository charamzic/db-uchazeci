package com.javajitsu.databazeuchazecu.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class TechnologyDetailRequestDTO {
    @Size(min = 2, message = "The name must be at least two characters long.")
    private String name;
    @Range(min = 1, max = 10, message = "The range is 1-10.")
    private Integer rating;
    private String note;
}
