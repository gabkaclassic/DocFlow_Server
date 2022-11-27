package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.entity.process.document.Document;
import server.entity.process.document.DocumentId;

/**
 * Репозиторий для сущности "Документ"
 * @see Document
 * */
@Repository
public interface DocumentRepository extends JpaRepository<Document, DocumentId>, CrudRepository<Document, DocumentId> {

}
