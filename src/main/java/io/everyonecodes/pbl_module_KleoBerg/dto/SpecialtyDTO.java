package io.everyonecodes.pbl_module_KleoBerg.dto;

import io.everyonecodes.pbl_module_KleoBerg.model.Provider;
import io.everyonecodes.pbl_module_KleoBerg.model.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyDTO {
    private Long id;
    private String name;
    private Set<ProviderSummaryDTO> providers;


    public SpecialtyDTO(Specialty specialty) {
        this.id = specialty.getId();
        this.name = specialty.getName();
        this.providers = specialty.getProviders().stream()
                .map(ProviderSummaryDTO::new)
                .collect(Collectors.toSet());
    }
}