package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.process.document.Document;
import server.entity.process.document.DocumentId;
import server.repository.DocumentRepository;

import java.util.Collection;

@Service
public class DocumentService {
    
    private final DocumentRepository repository;
    
    @Autowired
    public DocumentService(DocumentRepository repository) {
    
        this.repository = repository;
    }
    
    public void save(Document document) {
    
        repository.save(document);
    }
    
    public void saveAll(Collection<Document> documents) {
        
        repository.saveAll(documents);
    }
    
    public Document findById(String title, Long processId) {
        
        return repository.findById(new DocumentId(processId, title)).orElseThrow();
    }
    
}
