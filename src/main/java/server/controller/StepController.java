package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.controller.response.Response;
import server.controller.response.StepResponse;
import server.entity.process.step.StepId;
import server.entity.process.step.Step;
import server.service.ProcessService;
import server.service.StepService;

/**
 * Контроллер для обработки процессов, связанных с шагами
 * @see Step
 * */
@RestController
@RequestMapping("/step")
public class StepController {
    
    private final StepService stepService;
    private final ProcessService processService;
    
    @Autowired
    public StepController(StepService stepService, ProcessService processService) {
        
        this.stepService = stepService;
        this.processService = processService;
    }
    
    /**
     * Обработка запроса для перехода на следующий шаг
     * @see Response
     * */
    @GetMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response approve(@RequestParam String processId) {
        
        return processService.nextStep(processId);
    }
    
    /**
     * Обработка запроса для возврата на предыдущий шаг
     * @see Response
     * */
    @GetMapping(value = "/refuse", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response refuse(@RequestParam String processId) {
        
        return processService.previousStep(processId);
    }
    
    /**
     * Обработка получения полной информации о шаге
     * @see StepResponse
     * */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StepResponse getStepInfo(@RequestParam String title, @RequestParam String processId) {

        return stepService.getStep(new StepId(processId, title));
    }
}
