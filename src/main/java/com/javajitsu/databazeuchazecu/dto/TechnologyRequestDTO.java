package com.javajitsu.databazeuchazecu.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TechnologyRequestDTO {
    @Size(min = 2, message = "The technology name must have at least two letters.")
    private String name;
}
