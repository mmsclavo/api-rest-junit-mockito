package br.com.mms.apirestandtest.services.impl;

import br.com.mms.apirestandtest.domain.User;
import br.com.mms.apirestandtest.repositories.UserRepository;
import br.com.mms.apirestandtest.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }
}