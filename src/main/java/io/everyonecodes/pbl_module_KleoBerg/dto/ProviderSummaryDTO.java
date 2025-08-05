package io.everyonecodes.pbl_module_KleoBerg.dto;

import io.everyonecodes.pbl_module_KleoBerg.model.Provider;
import io.everyonecodes.pbl_module_KleoBerg.model.Review;
import io.everyonecodes.pbl_module_KleoBerg.model.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderSummaryDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<String> specialties;
    private Set<Review> reviews;


    public ProviderSummaryDTO(Provider provider) {
        this.id = provider.getId();
        this.firstName = provider.getFirstName();
        this.lastName = provider.getLastName();
        this.specialties = provider.getSpecialties().stream()
                .map(Specialty::getName)
                .collect(Collectors.toSet());
        this.reviews = provider.getReviews(); //REVIEWS auch zu einem DTO machen ?
    }
}
