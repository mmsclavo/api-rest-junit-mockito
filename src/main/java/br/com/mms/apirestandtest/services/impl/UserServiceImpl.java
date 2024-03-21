package br.com.mms.apirestandtest.services.impl;

import br.com.mms.apirestandtest.domain.User;
import br.com.mms.apirestandtest.domain.dto.UserDTO;
import br.com.mms.apirestandtest.repositories.UserRepository;
import br.com.mms.apirestandtest.services.UserService;
import br.com.mms.apirestandtest.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private ModelMapper mapper;

    public UserServiceImpl(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User create(UserDTO dto) {
        return repository.save(mapper.map(dto, User.class));
    }
}
