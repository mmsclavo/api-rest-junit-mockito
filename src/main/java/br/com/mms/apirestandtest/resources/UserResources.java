package br.com.mms.apirestandtest.resources;

import br.com.mms.apirestandtest.domain.dto.UserDTO;
import br.com.mms.apirestandtest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserResources {

    private ModelMapper mapper;
    private UserService userService;

    public UserResources(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok().body(userService.findAll().stream().map(u -> mapper.map(u, UserDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userService.create(dto).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
