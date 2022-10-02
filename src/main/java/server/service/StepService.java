package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.process.Step;
import server.repository.StepRepository;

import java.util.Optional;

@Service
public class StepService {
    
    private final StepRepository repository;
    
    @Autowired
    public StepService(StepRepository repository) {
        
        this.repository = repository;
    }
    
    public Optional<Step> findById(Long id) {
        
        var answer = repository.findById(id);
        
        if(answer.isPresent()) {
            var step = answer.get();
            
        }
        
        return answer;
    }
}
