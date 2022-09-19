package configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    private static final String HOST = "localhost";
    
    @Bean
    public ConnectionFactory connectionFactory(){
        
        return new CachingConnectionFactory(HOST);
    }
    
    @Bean
    public AmqpAdmin ampqAdmin() {
        
        return new RabbitAdmin(connectionFactory());
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate() {
        
        return new RabbitTemplate(connectionFactory());
    }
    
    @Bean
    public Queue queue() {
        
        return new Queue("queue");
    }
    
    public SimpleMessageListenerContainer messageListenerContainer() {
        
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(queue());
        container.setMessageListener(message -> System.out.println(new String(message.getBody())));
        
        return container;
    }
}
