package test.artplancom.TestTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import test.artplancom.TestTask.model.Animal;
import test.artplancom.TestTask.model.payload.NewAnimal;
import test.artplancom.TestTask.repository.AnimalRepository;
import test.artplancom.TestTask.service.security.UserDetailsImpl;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/home/animals")
public class AnimalController {
    
    @Autowired
    public AnimalRepository animalRepository;
    
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Animal>> getUserAnimals() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(
                animalRepository.findByUserId(
                        ((UserDetailsImpl) authentication.getPrincipal()).getId()));
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createAnimal(@RequestBody NewAnimal newAnimal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (animalRepository.existsByName(newAnimal.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: animal with name " + newAnimal.getName() + " is exist");
        }
        Animal animal = new Animal(newAnimal.getName(),
                newAnimal.getGender(),
                newAnimal.getBirthday(),
                newAnimal.getType(),
                ((UserDetailsImpl) authentication.getPrincipal()).getId());
        animalRepository.save(animal);
        return ResponseEntity.ok().body("Animal created successfully!");
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteAnimal(@PathVariable Long id) {
        if (animalRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: animal with id " + id + " not exist");
        }
        animalRepository.deleteById(id);
        return ResponseEntity.ok().body("Animal deleted successfully!");
    }
    
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAnimal(@PathVariable Long id) {
        if (animalRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: animal with id " + id + " not exist");
        }
        return ResponseEntity.ok().body(animalRepository.findById(id));
    }
    
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateAnimal(@PathVariable Long id, @RequestBody NewAnimal newAnimal) {
        if (animalRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: animal with id " + id + " not exist");
        }
        if (animalRepository.existsByName(newAnimal.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: animal with name " + newAnimal.getName() + " is exist");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Animal animal = new Animal(id,
                newAnimal.getName(),
                newAnimal.getGender(),
                newAnimal.getBirthday(),
                newAnimal.getType(),
                ((UserDetailsImpl) authentication.getPrincipal()).getId());
        animalRepository.save(animal);
        return ResponseEntity.ok().body(animal);
    }
}
