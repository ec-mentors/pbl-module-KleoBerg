package io.everyonecodes.BBRoutines.repository;

import io.everyonecodes.BBRoutines.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findOneByName(String name);
}