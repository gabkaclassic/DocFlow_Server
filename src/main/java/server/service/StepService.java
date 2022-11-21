package server.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.Step;
import server.entity.process.StepId;
import server.entity.process.document.Document;
import server.repository.StepRepository;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class StepService {
    
    private final StepRepository repository;
    
    private final SessionFactory sessionFactory;
    
    private final DocumentService documentService;
    
    private final ObjectWriter writer;
    
    @Autowired
    public StepService(StepRepository repository, SessionFactory sessionFactory, DocumentService documentService, ObjectWriter writer) {
        
        this.repository = repository;
        this.sessionFactory = sessionFactory;
        this.documentService = documentService;
        this.writer = writer;
    }
    
    public Step findById(StepId id) {
        
        
        return repository.findById(id).orElseThrow();
    }
    
    public StepResponse getStep(StepId stepId) {
        
        Step step;
        StepResponse response;
        
        try {
            
            step = findById(stepId);
    
            response = StepResponse.builder()
                    .step(step).build()
                    .status(Response.STATUS_SUCCESS)
                    .message(Response.SUCCESS_LOADING);
        }
        catch (NoSuchElementException e) {
    
            log.warn("No such step exception", e);
            
            response = StepResponse.builder().build()
                    .status(Response.STATUS_ERROR)
                    .message(e.getMessage());
        }
        
        return response;
    }
    public Response update(Step step) {
    
        for(var doc: step.getDocuments()) {
    
            if (documentStepRelationExists(step, doc))
                documentService.update(doc);
            else {
                try (var session = sessionFactory.openSession()) {
                    documentService.update(doc);
                    session.beginTransaction();
                    session.createNativeQuery(
                            String.format(
                                    "insert into step_documents" +
                                            "(process_id, title, documents_step_title, documents_title) values" +
                                            "('%s','%s','%s','%s')",
                                    step.getProcessId(),
                                    step.getTitle(),
                                    step.getTitle(),
                                    doc.getTitle()
                            )
                    ).executeUpdate();
                    session.getTransaction().commit();
                } catch (Exception e) {
                    log.error("Error in the process of update documents", e);
                    return Response.errorResponse(Response.SERVER_ERROR);
                }
            }
        }
        
        return Response.successResponse(Response.SUCCESS_LOADING);
    }
    
    private boolean documentStepRelationExists(Step step, Document doc) {
        
        try(var session = sessionFactory.openSession()) {
            return session.createNativeQuery(
                    String.format(
                            "select * from step_documents " +
                                    "where process_id='%s' and title='%s' and documents_title='%s'",
                            step.getProcessId(), step.getTitle(), doc.getTitle()
                    )
            ).uniqueResult() != null;
        }
    }
}
