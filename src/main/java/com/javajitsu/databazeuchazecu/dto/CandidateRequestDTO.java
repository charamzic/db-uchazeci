package com.javajitsu.databazeuchazecu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class CandidateRequestDTO {

    @Size(min = 2, message = "The name must have at least two characters.")
    private String name;

    @Email(message = "Ensure the email is in correct format, e.g., candidate@email.com.")
    private String email;
}
