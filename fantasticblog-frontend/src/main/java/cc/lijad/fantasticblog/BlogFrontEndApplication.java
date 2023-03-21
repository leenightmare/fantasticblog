package cc.lijad.fantasticblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ljd
 * @create 2023/2/14 13:07
 */
@EnableScheduling
@SpringBootApplication
public class BlogFrontEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogFrontEndApplication.class, args);
    }
}
