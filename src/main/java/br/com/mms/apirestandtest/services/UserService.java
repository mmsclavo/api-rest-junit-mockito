package br.com.mms.apirestandtest.services;

import br.com.mms.apirestandtest.domain.User;

public interface UserService {
    User findById(Integer id);
}
