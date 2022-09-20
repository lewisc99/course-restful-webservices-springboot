package com.in28minutes.rest.webservices.restfulwebservices.resources;


import com.in28minutes.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
public class UserResource {


    private UserDaoService service;
    public UserResource(UserDaoService service)
    {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers()
    {
        return service.findAll();
    }


    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id)
    {
        User user = service.findOne(id);
        if (user == null)
        {
            throw new UserNotFoundException("id: " + id);
        }
        return user;
    }


    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody User user)
    {
       User savedUser =  service.save(user);
        // /users/4 => /users /{id}, user.getID
        //we're going to get the full url and add a path {id} that will
        //be replaced by the id created (will be added location in the header);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public void DeleteUser(@PathVariable int id)
    {
        User user = service.findOne(id);
        if (user == null)
        {
            throw new UserNotFoundException("id: " + id);
        }
        service.deleteById(id);

    }



}
