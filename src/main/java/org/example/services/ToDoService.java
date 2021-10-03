package org.example.services;

import org.example.domain.Tag;
import org.example.domain.Todo;
import org.example.domain.User;
import org.example.domain.plainObject.TodoPojo;
import org.example.repositories.TodoRepository;
import org.example.repositories.UserRepository;
import org.example.services.inrerfaces.ITagService;
import org.example.services.inrerfaces.IToDoService;
import org.example.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * by Iskortsev S.V.
 */

@Service
public class ToDoService implements IToDoService {

    private final TodoRepository todoRepository;

    private final Converter converter;
    private final ITagService tagService;
    private final UserRepository userRepository;

    @Autowired
    public ToDoService(TodoRepository todoRepository,
                       Converter converter,
                       ITagService tagService,
                       UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.converter = converter;
        this.tagService = tagService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public TodoPojo createTodo(Todo todo, Long userId) {

        Optional<User> todoUser = userRepository.findById(userId);

        if (todoUser.isPresent()) {

            Set<Tag> tags = new HashSet<>();
            tags.addAll(todo.getTagList());

            todo.getTagList().clear();

            todo.setUser(todoUser.get());

            todoRepository.save(todo);

            tags.stream().map(tagService::findOrCreate).collect(Collectors.toSet()).forEach(todo::addTag);

            return converter.todoToPojo(todo);
        } else
            throw new NoSuchElementException("unable to get user for todo");
    }

    @Override
    @Transactional
    public TodoPojo getTodo(Long id, Long userId) {

        Optional<Todo> byId = todoRepository.findById(id);

        if (byId.isPresent()) {
            return converter.todoToPojo(byId.get());
        } else
            throw new NoSuchElementException("unable to get todo");

    }

    @Override
    @Transactional
    public List<TodoPojo> getAllTodos(Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return todoRepository.findAllByUser(userOptional.get()).stream().map(todo -> converter.todoToPojo(todo)).collect(Collectors.toList());
        } else {
            throw new NoSuchElementException("No user with id :" + userId +  " was found");
        }
    }

    @Override
    @Transactional
    public TodoPojo updateTodo(Todo source, Long todoId, Long userId) {

        Optional<Todo> targetOptional = todoRepository.findById(todoId);

        if (targetOptional.isPresent()) {
            Todo target = targetOptional.get();

            target.setName(source.getName());
            target.setComment(source.getComment());
            target.setStartDate(source.getStartDate());
            target.setEndDate(source.getEndDate());
            target.setImportant(source.getImportant());
            target.setPriority(source.getPriority());

            todoRepository.save(target);

            return converter.todoToPojo(target);
        } else {
            throw new NoSuchElementException("unable to update todo");
        }
    }

    @Override
    @Transactional
    public String deleteTodo(Long id, Long userId) {

        Optional<Todo> todoForDeleteOptional = todoRepository.findById(id);

        if (todoForDeleteOptional.isPresent()) {
            Todo todoForDelete = todoForDeleteOptional.get();
            //для избежания concurrency при удалении, создаем новый список с удалением у таг привязку к todoo
            new ArrayList<>(todoForDelete.getTagList()).forEach(tag -> tag.removeTodo(todoForDelete));
            todoRepository.delete(todoForDelete);
            return "Todo with id:" + id + " was successfully removed";
        } else
            throw new NoSuchElementException("unable to delete todo");

    }
}
