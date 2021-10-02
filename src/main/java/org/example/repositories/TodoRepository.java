package org.example.repositories;

import org.example.domain.Todo;
import org.example.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo,Long> {

    List<Todo> findAllByUser(User user);

}
