package br.com.mms.apirestandtest.services.impl;

import br.com.mms.apirestandtest.domain.User;
import br.com.mms.apirestandtest.domain.dto.UserDTO;
import br.com.mms.apirestandtest.repositories.UserRepository;
import br.com.mms.apirestandtest.services.UserService;
import br.com.mms.apirestandtest.services.exceptions.DataIntegratyViolationException;
import br.com.mms.apirestandtest.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String EMAIL_DUPLICADO = "Email já existe na base de usuários!";
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
        Optional<User> userExistente = repository.findByEmail(dto.getEmail());
        userExistente.orElseThrow(() -> new DataIntegratyViolationException(EMAIL_DUPLICADO));
        return repository.save(mapper.map(dto, User.class));
    }

    @Override
    public User update(Integer id, UserDTO dto) {
        Optional<User> userEncontrado = repository.findById(id);
        if(!userEncontrado.isPresent()){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
        Optional<User> userExistente = repository.findByEmail(dto.getEmail());
        if(userExistente.isPresent() && !userExistente.get().getId().equals(id)) {
            throw new DataIntegratyViolationException(EMAIL_DUPLICADO);
        }

        dto.setId(id);
        User userAtualizado = mapper.map(dto, User.class);
        return repository.save(userAtualizado);
    }

    @Override
    public void delete(Integer id) {
        Optional<User> userEncontrado = repository.findById(id);
        if(!userEncontrado.isPresent()){
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
        repository.deleteById(id);
    }
}
