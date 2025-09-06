package com.codearena.problem_service.controller;

import com.codearena.problem_service.dto.*;
import com.codearena.problem_service.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<ProblemResponse> createProblem(@RequestBody ProblemRequest request) {
        ProblemResponse response = problemService.createProblem(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProblemResponse>> getAllProblems() {
        List<ProblemResponse> problems = problemService.getAllProblems();
        return ResponseEntity.ok(problems);
    }

    // Implement update, getById, delete similarly using DTOs
}
