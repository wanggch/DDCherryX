package cn.ddcherry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DDCherryX main application.
 */
@SpringBootApplication(scanBasePackages = "cn.ddcherry")
public class DDCherryXApplication {

    public static void main(String[] args) {
        SpringApplication.run(DDCherryXApplication.class, args);
    }
}
