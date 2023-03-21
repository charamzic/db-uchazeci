package com.javajitsu.databazeuchazecu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Entity(name = "candidate_technology")
public class CandidateTechnology {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("candidateTechnologies")
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("candidateTechnologies")
    @JoinColumn(name = "technology_id")
    private Technology technology;

    private Integer rating;

    private String note;
}
