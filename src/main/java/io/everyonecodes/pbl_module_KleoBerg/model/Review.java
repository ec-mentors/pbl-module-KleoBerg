package io.everyonecodes.pbl_module_KleoBerg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", columnDefinition = "TEXT")
    private String comment;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "medical_provider_id", nullable = false)
    private MedicalProvider reviewedProvider;
}
