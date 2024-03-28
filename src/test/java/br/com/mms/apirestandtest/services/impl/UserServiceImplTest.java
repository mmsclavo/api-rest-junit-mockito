package br.com.mms.apirestandtest.services.impl;

import br.com.mms.apirestandtest.domain.User;
import br.com.mms.apirestandtest.domain.dto.UserDTO;
import br.com.mms.apirestandtest.repositories.UserRepository;
import br.com.mms.apirestandtest.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Mario";
    public static final String EMAIL = "mms@gmail.com";
    public static final String PASSWORD = "123";
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
    }

    @Test
    void whenFindByIdThenReturnUserInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(UserServiceImpl.OBJETO_NAO_ENCONTRADO));

        try {
            repository.findById(ID);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
            assertEquals(ex.getMessage(), UserServiceImpl.OBJETO_NAO_ENCONTRADO);
        }
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}