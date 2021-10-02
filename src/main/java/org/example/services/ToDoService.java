package org.example.services;

import org.example.domain.Tag;
import org.example.domain.Todo;
import org.example.domain.User;
import org.example.domain.plainObject.TodoPojo;
import org.example.services.inrerfaces.ITagService;
import org.example.services.inrerfaces.IToDoService;
import org.example.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * by Iskortsev S.V.
 */

@Service
public class ToDoService implements IToDoService {

    @PersistenceContext
    EntityManager entityManager;

    private final Converter converter;
    private final ITagService tagService;

    @Autowired
    public ToDoService(Converter converter, ITagService tagService) {
        this.converter = converter;
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public TodoPojo createTodo(Todo todo, Long userId) {

        User todoUser = entityManager
                .createQuery("SELECT user FROM User user WHERE user.id = :id", User.class)
                .setParameter("id", userId)
                .getSingleResult();
        todo.setUser(todoUser);

        Set<Tag> tags = new HashSet<>();
        tags.addAll(todo.getTagList());

        todo.getTagList().clear();

        entityManager.persist(todo);
        todo.setUser(todoUser);

        tags.stream().map(tagService::findOrCreate).collect(Collectors.toSet()).forEach(todo::addTag);

        return converter.todoToPojo(todo);
    }

    @Override
    public TodoPojo getTodo(long id) {
        return null;
    }

    @Override
    public List<TodoPojo> getAllTodos(Long userId) {
        return null;
    }

    @Override
    public TodoPojo updateTodo(Todo todo, long todoId) {
        return null;
    }

    @Override
    public TodoPojo deleteTodo(long id) {
        return null;
    }
}
