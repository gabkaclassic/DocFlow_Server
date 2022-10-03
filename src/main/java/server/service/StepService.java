package server.service;

import server.entity.process.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.repository.StepRepository;

@Service
public class StepService {
    
    @Autowired
    private StepRepository repository;
    
    public Step findById(Long id) {
    
        return repository.findById(id).orElse(new Step());
    }
}
