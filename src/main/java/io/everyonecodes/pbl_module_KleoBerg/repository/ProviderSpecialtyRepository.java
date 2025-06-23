package io.everyonecodes.pbl_module_KleoBerg.repository;

import io.everyonecodes.pbl_module_KleoBerg.model.MedicalProvider;
import io.everyonecodes.pbl_module_KleoBerg.model.ProviderSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderSpecialtyRepository extends JpaRepository<ProviderSpecialty, Long>   {

}
