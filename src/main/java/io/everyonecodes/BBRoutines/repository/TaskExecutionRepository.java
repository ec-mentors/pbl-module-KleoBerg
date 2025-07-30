package io.everyonecodes.BBRoutines.repository;

import io.everyonecodes.BBRoutines.model.TaskExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskExecutionRepository extends JpaRepository<TaskExecution, Long> {

}