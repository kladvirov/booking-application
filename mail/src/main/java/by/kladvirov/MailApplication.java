package by.kladvirov;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MailApplication {

    @SneakyThrows
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(MailApplication.class, args);
        Example example = context.getBean(Example.class);
        example.send();
    }

}
