package com.javajitsu.databazeuchazecu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class CandidateDetailRequestDTO {

    @Size(min = 2, message = "The name must be at least two characters long.")
    private String name;

    @Email(message = "Make sure your email is spelled correctly.")
    private String email;

    private Set<TechnologyDetailRequestDTO> technologies;
}
