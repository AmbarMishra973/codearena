package com.codearena.submission_service.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import com.codearena.submission_service.repository.SubmissionRepository;
import com.codearena.submission_service.model.Submission;
import com.codearena.submission_service.dto.SubmissionRequest;
import com.codearena.submission_service.dto.SubmissionResponse;
import com.codearena.submission_service.dto.Judge0Response;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository repository;

    @Autowired
    private Judge0Client judge0Client;

    public SubmissionResponse submitCode(SubmissionRequest request) {
        // Call Judge0
        Judge0Response result = judge0Client.execute(request);

        // Map result and save to DB
        Submission submission = new Submission();
        submission.setUserId(request.getUserId());
        submission.setProblemId(request.getProblemId());
        submission.setCode(request.getCode());
        submission.setLanguage(request.getLanguage());
        submission.setVerdict(result.getStatus());
        submission.setRuntime(result.getTime());
        submission.setMemory(result.getMemory());
        submission.setSubmittedAt(LocalDateTime.now());

        repository.save(submission);

        // Prepare response
        SubmissionResponse response = new SubmissionResponse();
        response.setVerdict(result.getStatus());
        response.setRuntime(result.getTime());
        response.setMemory(result.getMemory());
        response.setOutput(result.getStdout());
        response.setError(result.getStderr());
        return response;
    }
}
