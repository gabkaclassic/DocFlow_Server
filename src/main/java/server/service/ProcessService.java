package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.ExistResponse;
import server.controller.response.Response;
import server.entity.process.Process;
import server.entity.process.Step;
import server.entity.process.StepId;
import server.repository.ProcessRepository;
import server.repository.StepRepository;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProcessService {
    
    private final ProcessRepository repository;
    
    private final StepRepository stepRepository;
    
    private final DocumentService documentService;
    
    @Autowired
    public ProcessService(ProcessRepository repository, StepRepository stepRepository, DocumentService documentService) {
    
        this.repository = repository;
        this.stepRepository = stepRepository;
        this.documentService = documentService;
    }
    
    public void save(Process process) {
        
        repository.save(process);
    }
    public Response createProcess(Process process) {
    

        var id = UUID.randomUUID().toString();
        process.setId(id);
        process.getSteps().stream()
                .peek(step -> step.setProcessId(id))
                .flatMap(s -> s.getDocuments().stream())
                .forEach(d -> d.setProcessId(id));
        
        repository.save(process);
        
        var response = new Response();
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_CREATING);
        
        return response;
    }
    
    public Process findById(Long processId) {
        
        return repository.findById(processId).orElseThrow();
    }
    
    public ExistResponse exists(String title) {
    
        return ExistResponse.builder()
                .exist(repository.existsByTitle(title)).build()
                .status(Response.STATUS_SUCCESS);
    }
}
