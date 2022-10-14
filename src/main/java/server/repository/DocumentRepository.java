package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import server.entity.process.document.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long>, CrudRepository<Document, Long> {

}
