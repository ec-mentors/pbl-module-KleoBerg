package io.everyonecodes.BBRoutines.repository;

import io.everyonecodes.BBRoutines.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    Optional<Step> findOneByName(String name);
}