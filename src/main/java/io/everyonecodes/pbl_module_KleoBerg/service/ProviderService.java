package io.everyonecodes.pbl_module_KleoBerg.service;

import io.everyonecodes.pbl_module_KleoBerg.model.Provider;
import io.everyonecodes.pbl_module_KleoBerg.model.Review;
import io.everyonecodes.pbl_module_KleoBerg.model.Specialty;
import io.everyonecodes.pbl_module_KleoBerg.repository.ProviderRepository;
import io.everyonecodes.pbl_module_KleoBerg.repository.ReviewRepository;
import io.everyonecodes.pbl_module_KleoBerg.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;


    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;

    }


    @Transactional
    public Provider saveProvider(Provider provider) {
        System.out.println("Saving provider: " + provider.getFirstName() + " " + provider.getLastName());
        return providerRepository.save(provider);
    }

    @Transactional(readOnly = true)
    public Optional<Provider> findProviderById(Long id) {
        return providerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Provider> findAllProviders() {
        return providerRepository.findAll();
    }

    @Transactional
    public Provider updateProvider(Long id, Provider updatedProviderDetails) {
        return providerRepository.findById(id)
                .map(existingProvider -> {
                    existingProvider.setFirstName(updatedProviderDetails.getFirstName());
                    existingProvider.setLastName(updatedProviderDetails.getLastName());
                    System.out.println("Updating provider with ID: " + id);
                    return providerRepository.save(existingProvider);
                })
                .orElseThrow(() -> new RuntimeException("Provider not found with ID: " + id)); //not ideal make custom exception (and implement in controller), this will throw a 500 error. learn more about error handling and custom errors
    }

    @Transactional
    public void deleteProviderById(Long id) {
        System.out.println("Deleting provider with ID: " + id);
        providerRepository.deleteById(id);
    } //implement error if ID is not found


}