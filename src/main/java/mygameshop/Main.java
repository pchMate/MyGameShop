package mygameshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class Main {

    // satisfying checks.xml
    private Main() {
    }

    public static void main(final String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
