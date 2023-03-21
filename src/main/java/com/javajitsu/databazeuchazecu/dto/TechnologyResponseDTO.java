package com.javajitsu.databazeuchazecu.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TechnologyResponseDTO {

    private Long id;
    private String name;
}
