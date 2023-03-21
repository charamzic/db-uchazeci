package com.javajitsu.databazeuchazecu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorDetailsDTO {

    private ZonedDateTime timestamp;
    private String message;
    private String detail;
}
