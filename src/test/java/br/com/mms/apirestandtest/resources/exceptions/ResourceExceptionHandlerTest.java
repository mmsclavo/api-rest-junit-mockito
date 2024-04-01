package br.com.mms.apirestandtest.resources.exceptions;

import br.com.mms.apirestandtest.services.exceptions.ObjectNotFoundException;
import br.com.mms.apirestandtest.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundException() {
        ResponseEntity<StandardError> response =exceptionHandler.objectNotFound(new ObjectNotFoundException(UserServiceImpl.OBJETO_NAO_ENCONTRADO), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getClass(), ResponseEntity.class);
        assertEquals(response.getBody().getClass(), StandardError.class);
        assertEquals(response.getBody().getError(), UserServiceImpl.OBJETO_NAO_ENCONTRADO);
        assertEquals(response.getBody().getStatus(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    void dataIntegratyViolation() {
    }

    @Test
    void genericError() {
    }
}