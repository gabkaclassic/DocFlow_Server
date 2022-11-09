package server.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.ExistResponse;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.Process;
import server.repository.ProcessRepository;
import server.repository.StepRepository;

import java.util.UUID;

@Service
public class ProcessService {
    
    private final ProcessRepository repository;
    
    private final SessionFactory sessionFactory;
    
    @Autowired
    public ProcessService(ProcessRepository repository, SessionFactory sessionFactory) {
    
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }
    
    public void save(Process process) {
        
        repository.save(process);
    }
    
    public StepResponse nextStep(String processId) {
        
        var process = findById(processId);

        var step = process.nextStep();
    
        var session = sessionFactory.openSession();
        
        session.beginTransaction();
        
        session.createNativeQuery(String.format("update processes set current_step = %d where id = '%s'", process.getCurrentStep(), processId)).executeUpdate();
        session.getTransaction().commit();
        session.close();
        
        return StepResponse.builder()
                .step(step)
                .build().status(StepResponse.STATUS_SUCCESS).message(StepResponse.SUCCESS_LOADING);
    }
    
    public StepResponse previousStep(String processId) {
    
        var process = findById(processId);
    
        var step = process.previousStep();
    
        var session = sessionFactory.openSession();
    
        session.beginTransaction();
    
        session.createNativeQuery(String.format("update processes set current_step = %d where id = '%s'", process.getCurrentStep(), processId)).executeUpdate();
        session.getTransaction().commit();
        session.close();
    
        return StepResponse.builder()
                .step(step)
                .build().status(StepResponse.STATUS_SUCCESS).message(StepResponse.SUCCESS_LOADING);
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
