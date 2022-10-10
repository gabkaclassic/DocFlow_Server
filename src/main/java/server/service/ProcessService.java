package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.controller.response.Response;
import server.entity.Team;
import server.entity.process.Process;
import server.repository.ProcessRepository;

@Service
public class ProcessService {
    
    private final ProcessRepository repository;
    
    @Autowired
    public ProcessService(ProcessRepository repository) {
    
        this.repository = repository;
    }
    
    public void save(Process process) {
        
        repository.save(process);
    }
    
    public Response createProcess(Process process) {
        
        repository.save(process);
        
        var response = new Response();
        response.setStatus(Response.STATUS_SUCCESS);
        response.setMessage(Response.SUCCESS_CREATING);
        
        return response;
    }
}
