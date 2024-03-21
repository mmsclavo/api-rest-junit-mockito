package br.com.mms.apirestandtest.config;

import br.com.mms.apirestandtest.domain.User;
import br.com.mms.apirestandtest.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    private UserRepository repository;

    public LocalConfig(UserRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void startDB() {
        User u1 = new User(null, "mario", "mario@mail.com", "123");
        User u2 = new User(null, "jose", "jose@mail.com", "123");

        repository.saveAll(List.of(u1, u2));
    }
}
