package server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.ExistResponse;
import server.controller.response.Response;
import server.entity.process.document.Document;
import server.entity.process.document.DocumentId;
import server.repository.DocumentRepository;
import server.util.JSONUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    
    private final DocumentRepository repository;
    
    private final ObjectMapper mapper;
    
    @Autowired
    public DocumentService(DocumentRepository repository, ObjectMapper mapper) {
    
        this.repository = repository;
        this.mapper = mapper;
    }
    
    public void save(Document document) {
    
        repository.save(document);
    }
    
    public void saveAll(Collection<Document> documents) {
        
        repository.saveAll(documents);
    }
    
    public Document findById(String title, String processId) {
        
        return repository.findById(new DocumentId(processId, title)).orElseThrow();
    }
    
    public ExistResponse exists(DocumentId documentId) {
        
        return ExistResponse.builder()
                .exist(repository.existsById(documentId))
                .build().status(Response.STATUS_SUCCESS);
    }
    
    public Response update(String docs) {
    
        JSONUtils.splitObjects(docs, Document.class).forEach(repository::save);
        
        return Response.successResponse(Response.SUCCESS_CREATING);
    }
}
