package io.everyonecodes.pbl_module_KleoBerg.repository;

import io.everyonecodes.pbl_module_KleoBerg.model.MedicalProvider;
import io.everyonecodes.pbl_module_KleoBerg.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
