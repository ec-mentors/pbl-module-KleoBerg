package io.everyonecodes.pbl_module_KleoBerg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "provider_specialty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProviderSpecialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @ManyToMany(mappedBy = "providerSpecialties")
    private Set<MedicalProvider> medicalProviders;
}
