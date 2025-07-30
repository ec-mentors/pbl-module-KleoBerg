package io.everyonecodes.BBRoutines.repository;

import io.everyonecodes.BBRoutines.model.StepExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepExecutionRepository extends JpaRepository<StepExecution, Long> {

}