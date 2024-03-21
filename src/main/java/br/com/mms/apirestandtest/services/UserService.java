package br.com.mms.apirestandtest.services;

import br.com.mms.apirestandtest.domain.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    List<User> findAll();
}
