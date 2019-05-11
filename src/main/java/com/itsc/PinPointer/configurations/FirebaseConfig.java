package com.itsc.PinPointer.configurations;

import com.github.fabiomaffioletti.firebase.EnableFirebaseRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableFirebaseRepositories
public class FirebaseConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
