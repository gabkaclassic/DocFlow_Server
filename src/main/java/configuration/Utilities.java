package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utilities {
    
    @Bean
    public ObjectWriter jsonWriter() {
        
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
    
    @Bean
    public ObjectMapper jsonMapper() {
        
        return new ObjectMapper();
    }
}
