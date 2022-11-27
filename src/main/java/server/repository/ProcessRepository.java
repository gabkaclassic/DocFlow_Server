package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.entity.process.Process;
import server.entity.process.document.Document;

/**
 * Репозиторий для сущности "Процесс"
 * @see Process
 * */
@Repository
public interface ProcessRepository extends CrudRepository<Process, String>, JpaRepository<Process, String> {
    
    boolean existsByTitle(String title);
}
