package io.everyonecodes.pbl_module_KleoBerg.repository;

import io.everyonecodes.pbl_module_KleoBerg.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

}
