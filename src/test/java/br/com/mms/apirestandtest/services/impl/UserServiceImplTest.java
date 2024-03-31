package br.com.mms.apirestandtest.services.impl;

import br.com.mms.apirestandtest.domain.User;
import br.com.mms.apirestandtest.domain.dto.UserDTO;
import br.com.mms.apirestandtest.repositories.UserRepository;
import br.com.mms.apirestandtest.services.exceptions.DataIntegratyViolationException;
import br.com.mms.apirestandtest.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
            service.findById(ID);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
            assertEquals(ex.getMessage(), UserServiceImpl.OBJETO_NAO_ENCONTRADO);
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(service.findAll()).thenReturn(List.of(user));

        List<User> response = repository.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(0).getClass());

        assertEquals(ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(EMAIL, response.get(0).getEmail());
        assertEquals(PASSWORD, response.get(0).getPassword());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        String exMessage = null;
        try {
            User response = service.create(userDTO);
        } catch(Exception ex) {
            exMessage = ex.getMessage();
            assertEquals(ex.getClass(), DataIntegratyViolationException.class);
            assertEquals(ex.getMessage(), UserServiceImpl.EMAIL_DUPLICADO);
        }
        assertNotNull(exMessage);
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);
        when(repository.save(any())).thenReturn(user);

        User response = service.update(ID, userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        String exMessage = null;
        try {
            User response = service.update(2, userDTO);
        } catch(Exception ex) {
            exMessage = ex.getMessage();
            assertEquals(ex.getClass(), DataIntegratyViolationException.class);
            assertEquals(ex.getMessage(), UserServiceImpl.EMAIL_DUPLICADO);
        }
        assertNotNull(exMessage);
    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}