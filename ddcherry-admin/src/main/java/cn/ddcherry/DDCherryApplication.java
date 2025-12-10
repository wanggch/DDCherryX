package cn.ddcherry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DDCherryX main application.
 */
@SpringBootApplication(scanBasePackages = "cn.ddcherry")
public class DDCherryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DDCherryApplication.class, args);
    }
}
