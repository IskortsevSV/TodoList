package org.example.services.inrerfaces;

import org.example.domain.Todo;
import org.example.domain.plainObject.TodoPojo;

import java.util.List;

public interface IToDoService {
    TodoPojo createTodo(Todo todo, Long userId);
    TodoPojo getTodo(long id);
    TodoPojo updateTodo(Todo updateTodo, long userId);
    TodoPojo deleteTodo(long id);
    List<TodoPojo> getAllTodos(Long userId);
}
