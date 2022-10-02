package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.entity.process.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Long>, CrudRepository<Step, Long> {

}
