package io.everyonecodes.pbl_module_KleoBerg.dto;


import io.everyonecodes.pbl_module_KleoBerg.model.Review;
import io.everyonecodes.pbl_module_KleoBerg.model.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Specialty> specialties;
    private Set<Review> reviews;
}
