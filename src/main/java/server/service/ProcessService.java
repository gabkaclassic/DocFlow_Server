package server.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.ExistResponse;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.Process;
import server.entity.process.Step;
import server.entity.process.document.Document;
import server.repository.ProcessRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProcessService {
    
    private final ProcessRepository repository;
    
    private final SessionFactory sessionFactory;
    
    private final DocumentService documentService;
    
    @Autowired
    public ProcessService(ProcessRepository repository, SessionFactory sessionFactory, DocumentService documentService) {
    
        this.repository = repository;
        this.sessionFactory = sessionFactory;
        this.documentService = documentService;
    }
    
    public void save(Process process) {
        
        repository.save(process);
    }
    
    public Response nextStep(String processId) {
        
        Step nextStep = null;
        
        try(var session = sessionFactory.openSession()) {

            session.beginTransaction();
            
            session.createNativeQuery(changeStepQuery(processId, "+")).executeUpdate();
            
            var currentStepNumber = session.createQuery(
                    String.format(
                            "select process.currentStep from Process process where process.id = '%s'",
                            processId
                    ),
                    Integer.class
            ).uniqueResult();
            
            nextStep = session.createQuery(
                    String.format(
                            "from Step where process_id = '%s' and number = %d",
                            processId, currentStepNumber
                            ),
                    Step.class
            ).uniqueResult();
            
            var currentStep = session.createQuery(
                    String.format(
                            "from Step where process_id = '%s' and number = %d",
                            processId, currentStepNumber - 1
                    ),
                    Step.class
            ).uniqueResult();
            
            session.createNativeQuery(
                    String.format(
                            "update step_documents set title = '%s' where process_id = '%s' and title = '%s'",
                            nextStep.getTitle(), processId, currentStep.getTitle()
                    )
            ).executeUpdate();
            
            session.getTransaction().commit();
            
        }
        catch(Exception e) {
            return Response.errorResponse(Response.SERVER_ERROR);
        }
        
        
        return Response.successResponse(Response.SUCCESS_LOADING);
    }
    
    public Response previousStep(String processId) {
    
        Step previousStep = null;
    
        try(var session = sessionFactory.openSession()) {
        
            session.beginTransaction();
        
            session.createNativeQuery(changeStepQuery(processId, "-")).executeUpdate();
        
            var currentStepNumber = session.createQuery(
                    String.format(
                            "select process.currentStep from Process process where process.id = '%s'",
                            processId
                    ),
                    Integer.class
            ).uniqueResult();
    
            previousStep = session.createQuery(
                    String.format(
                            "from Step where process_id = '%s' and number = %d",
                            processId, currentStepNumber
                    ),
                    Step.class
            ).uniqueResult();
        
            var currentStep = session.createQuery(
                    String.format(
                            "from Step where process_id = '%s' and number = %d",
                            processId, currentStepNumber + 1
                    ),
                    Step.class
            ).uniqueResult();
        
            session.createNativeQuery(
                    String.format(
                            "update step_documents set title = '%s' where process_id = '%s' and title = '%s' and documents_step_title = '%s'",
                            previousStep.getTitle(), processId, currentStep.getTitle(), previousStep.getTitle()
                    )
            ).executeUpdate();
            
            session.getTransaction().commit();
            
        }
        catch(Exception e) {
            return Response.errorResponse(Response.SERVER_ERROR);
        }
    
    
        return Response.successResponse(Response.SUCCESS_LOADING);
    }
    
    private String changeStepQuery(String processId, String operation) {
        
        return String.format(
                "update processes set current_step = " +
                        "(select current_step from processes where id = '%s') %s 1 " +
                        "where id = '%s'",
                processId, operation, processId
        );
    }
    public Response createProcess(Process process) {
    

        var id = UUID.randomUUID().toString();
        process.setId(id);
        for(var step: process.getSteps()) {
            
            var title = step.getTitle();
            
            step.getDocuments().forEach(document -> document.setStepTitle(title));
        }
        
        
        repository.save(process);
        
        var response = new Response();
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_CREATING);
        
        return response;
    }
    
    public Process findById(String processId) {
        
        return repository.findById(processId).orElseThrow();
    }
    
    public ExistResponse exists(String title) {
    
        return ExistResponse.builder()
                .exist(repository.existsByTitle(title)).build()
                .status(Response.STATUS_SUCCESS);
    }
}
