package io.everyonecodes.pbl_module_KleoBerg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(columnDefinition = "TEXT")
    private String comment;
    private double rating;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore //remove when DTOs implemented
    private Provider reviewedProvider;


    public Review(String comment, double rating, Provider reviewedProvider) {
        this.reviewedProvider = reviewedProvider;
        this.rating = rating;
        this.comment = comment;
    }
}
