package com.in28minutes.rest.webservices.restfulwebservices.resources;


import com.in28minutes.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity createUser(@RequestBody User user)
    {
       User savedUser =  service.save(user);
        // /users/4 => /users /{id}, user.getID
        //we're going to get the full url and add a path {id} that will
        //be replaced by the id created (will be added location in the header);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }



}
