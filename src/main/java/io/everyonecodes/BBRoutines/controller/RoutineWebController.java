package io.everyonecodes.BBRoutines.controller;

import io.everyonecodes.BBRoutines.dto.RoutineDto;
import io.everyonecodes.BBRoutines.service.RoutineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/routines")
public class RoutineWebController {

    private final RoutineService routineService;

    public RoutineWebController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping
    public String getAllRoutines(Model model) {
        model.addAttribute("routines", routineService.findAllRoutines());
        return "routines";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        // --- CHANGE THIS ---
        // Let's create a more robust DTO to avoid issues
        RoutineDto newRoutine = new RoutineDto();
        newRoutine.setIsActive(true); // Set a sensible default
        model.addAttribute("routine", newRoutine);
        // -------------------
        return "routine-form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("routine", routineService.findRoutineById(id));
        return "routine-form";
    }

    @PostMapping("/save")
    public String saveRoutine(@ModelAttribute("routine") RoutineDto routine) { // Explicitly name the model attribute
        // The rest of the logic is likely fine.
        if (routine.getId() == null) {
            routineService.createRoutine(routine);
        } else {
            routineService.updateRoutine(routine.getId(), routine);
        }
        return "redirect:/web/routines";
    }

    @GetMapping("/{id}/delete")
    public String deleteRoutine(@PathVariable Long id) {
        routineService.deleteRoutineById(id);
        return "redirect:/web/routines";
    }
}