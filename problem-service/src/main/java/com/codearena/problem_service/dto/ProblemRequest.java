package com.codearena.problem_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemRequest {
    private String title;
    private String description;
    private String inputFormat;
    private String outputFormat;
    private String difficulty;
}
