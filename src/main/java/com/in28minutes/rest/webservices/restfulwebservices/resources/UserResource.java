package com.in28minutes.rest.webservices.restfulwebservices.resources;


import com.in28minutes.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.models.User;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.stream;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {


    private UserDaoService service;
    public UserResource(UserDaoService service)
    {
        this.service = service;
    }

    @GetMapping("/users")
    ResponseEntity<CollectionModel<EntityModel<User>>> retrieveAllUsers()
    {

        List<EntityModel<User>> users = StreamSupport.stream(service.findAll().spliterator(), false)
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserResource.class).retrieveUser(user.getId())).withSelfRel(), //
                        linkTo(methodOn(UserResource.class).retrieveAllUsers()).withRel("getAll"))) //
                .collect(Collectors.toList());


        return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(UserResource.class).retrieveAllUsers()).withSelfRel()));

    }



    //Entity Model
    //WebMvcLinkBuilder
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id)
    {
        User user = service.findOne(id);
        if (user == null)
        {
            throw new UserNotFoundException("id: " + id);
        }
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveUser(id));
       entityModel.add(link.withRel("get-user-by-id"));

        return entityModel;
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
