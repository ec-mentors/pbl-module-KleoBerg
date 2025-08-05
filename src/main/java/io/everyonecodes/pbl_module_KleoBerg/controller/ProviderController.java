package io.everyonecodes.pbl_module_KleoBerg.controller;


import io.everyonecodes.pbl_module_KleoBerg.dto.ProviderDTO;
import io.everyonecodes.pbl_module_KleoBerg.model.Provider;
import io.everyonecodes.pbl_module_KleoBerg.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public List<ProviderDTO> getAllProviders() {
        System.out.println("Received request to get all providers");
        return providerService.findAllProviders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getProviderById(@PathVariable Long id) {
        System.out.println("Received request to get provider with ID: " + id);
        return providerService.findProviderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    System.out.println("Provider with ID " + id + " not found.");
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Provider createProvider(@RequestBody Provider provider) {
        System.out.println("Received request to create provider: " + provider.getFirstName());
        return providerService.saveProvider(provider);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody Provider updatedProviderDetails) {
        System.out.println("Received request to update provider with ID: " + id);
        try {
            Provider updatedProvider = providerService.updateProvider(id, updatedProviderDetails);
            return ResponseEntity.ok(updatedProvider);
        } catch (RuntimeException e) {
            System.out.println("Error updating provider with ID " + id + ": " + e.getMessage());
            if (e.getMessage().contains("Provider not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable Long id) {
        System.out.println("Received request to delete provider with ID: " + id);
        providerService.deleteProviderById(id);
    } //implement error if id is not found
}
