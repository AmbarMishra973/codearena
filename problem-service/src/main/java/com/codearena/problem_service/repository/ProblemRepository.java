package com.codearena.problem_service.repository;

import com.codearena.problem_service.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    // Custom query to fetch all problem IDs
    @Query("SELECT p.id FROM Problem p")
    List<Long> findAllProblemIds();
}
