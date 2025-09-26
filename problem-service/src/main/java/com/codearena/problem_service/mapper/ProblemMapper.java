package com.codearena.problem_service.mapper;

import com.codearena.problem_service.dto.ProblemRequest;
import com.codearena.problem_service.dto.ProblemResponse;
import com.codearena.problem_service.entity.Problem;

public class ProblemMapper {

    public static Problem toEntity(ProblemRequest request) {
        if (request == null) {
            return null;
        }
        return Problem.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .inputFormat(request.getInputFormat())
                .outputFormat(request.getOutputFormat())
                .difficulty(request.getDifficulty())
                .build();
    }

    public static ProblemResponse toResponse(Problem problem) {
        if (problem == null) {
            return null;
        }
        return ProblemResponse.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .inputFormat(problem.getInputFormat())
                .outputFormat(problem.getOutputFormat())
                .difficulty(problem.getDifficulty())
                .build();
    }
}
