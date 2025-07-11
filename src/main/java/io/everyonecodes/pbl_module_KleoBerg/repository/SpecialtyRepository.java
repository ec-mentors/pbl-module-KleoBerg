package io.everyonecodes.pbl_module_KleoBerg.repository;

import io.everyonecodes.pbl_module_KleoBerg.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long>   {

}
