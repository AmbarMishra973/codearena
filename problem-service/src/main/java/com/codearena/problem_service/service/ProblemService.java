package com.codearena.problem_service.service;

import com.codearena.problem_service.dto.*;
import com.codearena.problem_service.entity.Problem;
import com.codearena.problem_service.mapper.ProblemMapper;
import com.codearena.problem_service.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemResponse createProblem(ProblemRequest request) {
        Problem problem = ProblemMapper.toEntity(request);
        Problem saved = problemRepository.save(problem);
        return ProblemMapper.toResponse(saved);
    }

    public List<ProblemResponse> getAllProblems() {
        return problemRepository.findAll().stream()
                .map(ProblemMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Add other methods (update, delete, getById) similarly using DTOs
}
