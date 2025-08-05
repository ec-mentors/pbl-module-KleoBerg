package io.everyonecodes.BBRoutines.config;

import io.everyonecodes.BBRoutines.model.*;
import io.everyonecodes.BBRoutines.repository.RoutineExecutionRepository;
import io.everyonecodes.BBRoutines.repository.RoutineRepository;
import io.everyonecodes.BBRoutines.repository.StepRepository;
import io.everyonecodes.BBRoutines.repository.TaskRepository;
import io.everyonecodes.BBRoutines.service.RoutineExecutionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoutineRepository routineRepository;
    private final TaskRepository taskRepository;
    private final StepRepository stepRepository;
    private final RoutineExecutionService routineExecutionService;
    private final RoutineExecutionRepository routineExecutionRepository;

    public DataLoader(RoutineRepository routineRepository,
                      TaskRepository taskRepository,
                      StepRepository stepRepository,
                      RoutineExecutionService routineExecutionService,
                      RoutineExecutionRepository routineExecutionRepository) {
        this.routineRepository = routineRepository;
        this.taskRepository = taskRepository;
        this.stepRepository = stepRepository;
        this.routineExecutionService = routineExecutionService;
        this.routineExecutionRepository = routineExecutionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // --- 1. Clear all tables and reset ID sequences ---
        routineRepository.truncateAllTablesAndRestartIds();
        System.out.println("--- All tables truncated and ID sequences reset ---");
        System.out.println("--- Initializing Database with Test Data ---");

        // --- 2. Create and Save all reusable STEP definitions ---
        Step stepPants = stepRepository.save(Step.builder().name("Put on pants").build());
        Step stepShirt = stepRepository.save(Step.builder().name("Put on shirt").build());
        Step stepSocks = stepRepository.save(Step.builder().name("Put on socks & shoes").build());
        Step stepJacket = stepRepository.save(Step.builder().name("Put on jacket").build());
        Step stepBrushTeeth = stepRepository.save(Step.builder().name("Brush teeth").audioCueUrl("/audio/brush.mp3").build());
        Step stepWashFace = stepRepository.save(Step.builder().name("Wash face").build());
        Step stepEatCereal = stepRepository.save(Step.builder().name("Eat cereal").iconUrl("/icons/cereal.png").build());
        Step stepDrinkJuice = stepRepository.save(Step.builder().name("Drink juice").iconUrl("/icons/juice.png").build());
        Step stepPackLunch = stepRepository.save(Step.builder().name("Pack lunchbox").build());

        // --- 3. Create, assemble, and Save all reusable TASK definitions ---
        Task taskGetDressed = Task.builder().name("Get Dressed").iconUrl("/icons/clothes.png").build();
        taskGetDressed.addTaskStep(TaskStep.builder().sequenceOrder(1).expectedDurationSeconds(120).step(stepPants).build());
        taskGetDressed.addTaskStep(TaskStep.builder().sequenceOrder(2).expectedDurationSeconds(90).step(stepShirt).build());
        taskGetDressed = taskRepository.save(taskGetDressed);

        Task taskBathroom = Task.builder().name("Bathroom Time").iconUrl("/icons/bathroom.png").build();
        taskBathroom.addTaskStep(TaskStep.builder().sequenceOrder(1).expectedDurationSeconds(120).step(stepBrushTeeth).build());
        taskBathroom.addTaskStep(TaskStep.builder().sequenceOrder(2).expectedDurationSeconds(60).step(stepWashFace).build());
        taskBathroom = taskRepository.save(taskBathroom);

        Task taskBreakfast = Task.builder().name("Eat Breakfast").iconUrl("/icons/breakfast.png").build();
        taskBreakfast.addTaskStep(TaskStep.builder().sequenceOrder(1).expectedDurationSeconds(300).step(stepEatCereal).build());
        taskBreakfast.addTaskStep(TaskStep.builder().sequenceOrder(2).expectedDurationSeconds(30).step(stepDrinkJuice).build());
        taskBreakfast = taskRepository.save(taskBreakfast);

        Task taskGetReady = Task.builder().name("Get Ready to Leave").iconUrl("/icons/door.png").build();
        taskGetReady.addTaskStep(TaskStep.builder().sequenceOrder(1).expectedDurationSeconds(60).step(stepSocks).build());
        taskGetReady.addTaskStep(TaskStep.builder().sequenceOrder(2).expectedDurationSeconds(45).step(stepJacket).build());
        taskGetReady.addTaskStep(TaskStep.builder().sequenceOrder(3).expectedDurationSeconds(30).step(stepPackLunch).build());
        taskGetReady = taskRepository.save(taskGetReady);

        // --- 4. Create, assemble, and Save the ROUTINE definition ---
        Routine morningRoutine = Routine.builder()
                .name("Morning School Routine")
                .description("Everything you need to do to get ready for school on time!")
                .totalExpectedDurationSeconds(1800) // 30 minutes
                .isActive(true)
                .build();

        morningRoutine.addRoutineTask(RoutineTask.builder().sequenceOrder(1).expectedDurationSeconds(300).task(taskGetDressed).build());
        morningRoutine.addRoutineTask(RoutineTask.builder().sequenceOrder(2).expectedDurationSeconds(240).task(taskBathroom).build());
        morningRoutine.addRoutineTask(RoutineTask.builder().sequenceOrder(3).expectedDurationSeconds(600).task(taskBreakfast).build());
        morningRoutine.addRoutineTask(RoutineTask.builder().sequenceOrder(4).expectedDurationSeconds(200).task(taskGetReady).build());

        morningRoutine = routineRepository.save(morningRoutine); // Re-assign to get the saved entity with ID

        // --- 5. Create a sample RoutineExecution for testing ---
        System.out.println("--- Creating a sample in-progress Routine Execution ---");
        routineExecutionService.startRoutine(morningRoutine.getId());

        // --- 6. Print confirmation to the console ---
        System.out.println("--- Database Initialized with Test Data ---");
        System.out.println("Routines loaded: " + routineRepository.count());
        System.out.println("Tasks loaded: " + taskRepository.count());
        System.out.println("Steps loaded: " + stepRepository.count());
        System.out.println("Routine Executions loaded: " + routineExecutionRepository.count());
        System.out.println("-----------------------------------------");
    }
}