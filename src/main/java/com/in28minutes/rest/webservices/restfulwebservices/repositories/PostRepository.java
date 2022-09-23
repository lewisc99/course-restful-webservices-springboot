package com.in28minutes.rest.webservices.restfulwebservices.repositories;

import com.in28minutes.rest.webservices.restfulwebservices.models.Post;
import com.in28minutes.rest.webservices.restfulwebservices.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {


}
