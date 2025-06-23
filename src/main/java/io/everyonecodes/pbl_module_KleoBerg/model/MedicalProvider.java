package io.everyonecodes.pbl_module_KleoBerg.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "medical_provider")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @ManyToMany
    @JoinTable(
            name="medical_provider_specialty",
            joinColumns = @JoinColumn(name = "medical_provider_id"),
            inverseJoinColumns = @JoinColumn(name = "provider_specialty_id")
    )
    private Set<ProviderSpecialty> providerSpecialties;

    @OneToMany(mappedBy = "reviewedProvider")
    private Set<Review> reviews;
}


// snake cas configuration to snake case

//@configuration
