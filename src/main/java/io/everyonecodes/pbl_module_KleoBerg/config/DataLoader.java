package io.everyonecodes.pbl_module_KleoBerg.config;

import io.everyonecodes.pbl_module_KleoBerg.model.Provider;
import io.everyonecodes.pbl_module_KleoBerg.model.Review;
import io.everyonecodes.pbl_module_KleoBerg.model.Specialty;
import io.everyonecodes.pbl_module_KleoBerg.repository.ProviderRepository;
import io.everyonecodes.pbl_module_KleoBerg.repository.ReviewRepository;
import io.everyonecodes.pbl_module_KleoBerg.repository.SpecialtyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProviderRepository providerRepository;
    private final SpecialtyRepository specialtyRepository;
    private final ReviewRepository reviewRepository;


    public DataLoader(ProviderRepository providerRepository,
                      SpecialtyRepository specialtyRepository, 
                      ReviewRepository reviewRepository) {
        this.providerRepository = providerRepository;
        this.specialtyRepository = specialtyRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        clearAllTables();

        
        Specialty psych = new Specialty();
        psych.setName("Psychologist");
        Specialty gp = new Specialty();
        gp.setName("General Practitioner");
        Specialty derm = new Specialty();
        derm.setName("Dermatologist");

        psych = specialtyRepository.save(psych);
        gp = specialtyRepository.save(gp);
        derm = specialtyRepository.save(derm);

        
        
        Provider provider1 = new Provider();
        provider1.setFirstName("Alice");
        provider1.setLastName("Wonder");
        provider1.setSpecialties(new HashSet<>(Set.of(derm, gp))); 

        Provider provider2 = new Provider();
        provider2.setFirstName("Barabara");
        provider2.setLastName("Builder");
        provider2.setSpecialties(new HashSet<>(Set.of(derm)));

        Provider provider3 = new Provider();
        provider3.setFirstName("Charlotte");
        provider3.setLastName("Cha");
        provider3.setSpecialties(new HashSet<>(Set.of(psych, gp)));

        provider1 = providerRepository.save(provider1);
        provider2 = providerRepository.save(provider2);
        provider3 = providerRepository.save(provider3);

        
        
        Review review1 = new Review("Great doctor, very empathetic!", 5.0, provider1);
        Review review2 = new Review("Knowledgeable, but wait times are long.", 3.5, provider1);
        Review review3 = new Review("Excellent service!", 4.0, provider2);
        Review review4 = new Review("Highly recommended psychologist.", 5.0, provider3);

        reviewRepository.saveAll(Arrays.asList(review1, review2, review3, review4));

        System.out.println("--- Database Initialized with Test Data ---");
        System.out.println("Providers: " + providerRepository.count());
        System.out.println("Specialties: " + specialtyRepository.count());
        System.out.println("Reviews: " + reviewRepository.count());
    }

    
    private void clearAllTables() {
        reviewRepository.deleteAllInBatch(); 
        providerRepository.deleteAllInBatch(); 
        specialtyRepository.deleteAllInBatch(); 
        System.out.println("--- Cleared Existing Data ---");
    }
}


