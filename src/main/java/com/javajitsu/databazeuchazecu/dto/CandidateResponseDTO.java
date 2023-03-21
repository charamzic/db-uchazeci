package com.javajitsu.databazeuchazecu.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class CandidateResponseDTO {

    private Long id;
    private String name;
    private String email;

}
