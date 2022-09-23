package com.in28minutes.rest.webservices.restfulwebservices.resources;


import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.models.Post;
import com.in28minutes.rest.webservices.restfulwebservices.models.User;
import com.in28minutes.rest.webservices.restfulwebservices.repositories.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {


    private UserRepository repository;
    private PostRepository postRepository;

    public UserJpaResource( UserRepository repository,PostRepository postRepository )
    {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    ResponseEntity<CollectionModel<EntityModel<User>>> retrieveAllUsers()
    {

        List<EntityModel<User>> users = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserJpaResource.class).retrieveUser(user.getId())).withSelfRel(), //
                        linkTo(methodOn(UserJpaResource.class).retrieveAllUsers()).withRel("getAll"))) //
                .collect(Collectors.toList());


        return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(UserJpaResource.class).retrieveAllUsers()).withSelfRel()));

    }



    //Entity Model
    //WebMvcLinkBuilder
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id)
    {
       Optional<User> user = repository.findById(id);
        if (user.isEmpty())
        {
            throw new UserNotFoundException("id: " + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveUser(id));
       entityModel.add(link.withRel("get-user-by-id"));

        return entityModel;
    }


    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody User user)
    {
       User savedUser =  repository.save(user);
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
        Optional<User> user = repository.findById(id);
        if (user.isEmpty())
        {
            throw new UserNotFoundException("id: " + id);
        }
        repository.deleteById(id);
    }



    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostForUser(@PathVariable int id)
    {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty())
        {
            throw new UserNotFoundException("id: " + id);
        }

        return user.get().getPosts();

    }



    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post)
    {

        Optional<User> user = repository.findById(id);

        if (user.isEmpty())
        {
            throw new UserNotFoundException("id: " + id);
        }

        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return  ResponseEntity.created(location).build();

    }






}
