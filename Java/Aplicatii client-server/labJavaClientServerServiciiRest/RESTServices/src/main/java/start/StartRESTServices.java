package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("repository")
@ComponentScan("services")
@Configuration
@SpringBootApplication
public class StartRESTServices {
    public static void main(String[] args) {
        SpringApplication.run(StartRESTServices.class, args);
    }
}
