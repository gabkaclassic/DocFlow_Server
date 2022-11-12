package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.ExistResponse;
import server.controller.response.Response;
import server.entity.process.document.Document;
import server.entity.process.document.DocumentId;
import server.repository.DocumentRepository;
import server.util.JSONUtils;

@Service
public class DocumentService {
    
    private final DocumentRepository repository;
    
    @Autowired
    public DocumentService(DocumentRepository repository) {
    
        this.repository = repository;
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
