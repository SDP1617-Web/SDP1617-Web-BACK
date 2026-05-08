package com.sdp1617.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Sdp1617WebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sdp1617WebServerApplication.class, args);
    }

}
