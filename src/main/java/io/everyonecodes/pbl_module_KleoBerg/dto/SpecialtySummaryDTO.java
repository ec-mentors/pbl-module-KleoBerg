package io.everyonecodes.pbl_module_KleoBerg.dto;

import io.everyonecodes.pbl_module_KleoBerg.model.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtySummaryDTO {
    private Long id;
    private String name;


    public SpecialtySummaryDTO(Specialty specialty) {
        this.id = specialty.getId();
        this.name = specialty.getName();
    }
}
