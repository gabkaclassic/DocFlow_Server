package server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс-генератор бинов, которые используются как утилиты
 * */
@Configuration
public class Utilities {
    
    /**
     * JSON-serializer bean
     * */
    @Bean
    public ObjectWriter jsonWriter() {
    
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
    
    /**
     * JSON-deserializer bean
     * */
    @Bean
    public ObjectMapper jsonMapper() {
        
        return new ObjectMapper();
    }
}
