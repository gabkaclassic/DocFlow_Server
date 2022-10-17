package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.entity.process.document.Document;
import server.repository.DocumentRepository;

import java.util.Collection;

@Service
public class DocumentService {
    
    @Autowired
    private DocumentRepository repository;
    
    public void save(Document document) {
        
        repository.save(document);
    }
    
    public void saveAll(Collection<Document> documents) {
        
        repository.saveAll(documents);
    }
    
    public Document findById(String id) {
        
        return repository.findById(id).orElseThrow();
    }
    
}
