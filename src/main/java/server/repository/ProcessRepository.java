package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.entity.process.Process;

@Repository
public interface ProcessRepository extends CrudRepository<Process, Long>, JpaRepository<Process, Long> {

}
