package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.entity.process.Process;
import server.repository.ProcessRepository;
import server.repository.StepRepository;

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
    
            repository.save(process);
            process.getSteps().stream()
                    .flatMap(step -> step.getDocuments().stream())
                    .distinct()
                    .forEach(documentService::save);
    
            var response = new Response();
            response.setStatus(Response.STATUS_SUCCESS);
            response.setMessage(Response.SUCCESS_CREATING);
            
            return response;
    }
    
    public Process findById(Long processId) {
        
        return repository.findById(processId).orElseThrow();
    }
}
