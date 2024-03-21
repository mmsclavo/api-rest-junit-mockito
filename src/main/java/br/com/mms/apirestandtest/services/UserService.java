package br.com.mms.apirestandtest.services;

import br.com.mms.apirestandtest.domain.User;
import br.com.mms.apirestandtest.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO dto);
}
