package io.everyonecodes.BBRoutines.repository;

import io.everyonecodes.BBRoutines.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {


    /**
     * -->>> ONLY FOR DEVELOPEMENT, don't use truncate in user code
     *
     * This is a custom native SQL query to efficiently clear all data.
     * TRUNCATE is faster than DELETE and resets table metadata.
     * RESTART IDENTITY resets the auto-incrementing ID counters back to 1.
     * CASCADE automatically removes all dependent rows in other tables (e.g., routine_task),
     * which solves the foreign key constraint violation error.
     * NOTE: This is for development/testing environments only.
     */
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE routine, task, step, routine_task, task_step RESTART IDENTITY CASCADE", nativeQuery = true)
    void truncateAllTablesAndRestartIds();
}