package test.artplancom.TestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.artplancom.TestTask.model.Animal;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    
    List<Animal> findByUserId(Long userId);
    
    Optional<Animal> findById(Long id);
    
    Boolean existsByName(String name);
    
    boolean existsById(Long id);
}
