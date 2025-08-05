package io.everyonecodes.pbl_module_KleoBerg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn,
            inverseJoinColumns = @JoinColumn
    )
    private Set<Specialty> specialties = new HashSet<>(); //initiialized zu einem HashSet damit es nie null ist.

    @OneToMany(mappedBy = "reviewedProvider")
    private Set<Review> reviews =  new HashSet<>();
}


