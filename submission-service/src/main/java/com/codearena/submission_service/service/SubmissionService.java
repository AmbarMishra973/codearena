package com.codearena.submission_service.service;

import com.codearena.submission_service.dto.*;
import com.codearena.submission_service.model.Submission;
import com.codearena.contest_service.model.Contest;
import com.codearena.contest_service.repository.ContestRepository;
import com.codearena.submission_service.repository.SubmissionRepository;
import com.codearena.plagiarism_service.PlagiarismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private PlagiarismService plagiarismService;  // Injecting PlagiarismService

    // Submit code to contest
    public SubmissionResponse submitCode(SubmissionRequest request) {
        // Fetch the contest by its ID
        Contest contest = contestRepository.findById(request.getContestId()).orElseThrow(
                () -> new RuntimeException("Contest not found")
        );

        // Check if the contest has ended
        if (contest.getEndTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("This contest has ended. No more submissions allowed.");
        }

        // Process submission
        Submission submission = new Submission();
        submission.setUserId(request.getUserId());
        submission.setProblemId(request.getProblemId());
        submission.setCode(request.getCode());
        submission.setLanguage(request.getLanguage());
        submission.setSubmittedAt(LocalDateTime.now());

        // Check for plagiarism
        double similarityPercentage = plagiarismService.checkPlagiarism(request.getCode());
        if (plagiarismService.isSuspicious(similarityPercentage)) {
            // Flag as suspicious if similarity exceeds the threshold
            submission.setVerdict("Suspicious - Possible Plagiarism");
        } else {
            submission.setVerdict("Accepted");
        }

        // Save submission to DB
        submissionRepository.save(submission);

        // Return response
        return new SubmissionResponse("Submission received", submission.getId(), submission.getVerdict());
    }
}
