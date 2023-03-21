package cc.lijad.fantasticblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ljd
 * @create 2023/2/17 16:09
 */
@EnableTransactionManagement
@SpringBootApplication
public class BlogBackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogBackEndApplication.class, args);
    }
}
