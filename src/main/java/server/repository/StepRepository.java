package server.repository;

import server.entity.process.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Long>,
        CrudRepository<Step, Long> {
    
    Optional<Step> findById(Long id);
}
