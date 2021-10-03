package org.example.services.inrerfaces;

import org.example.domain.Todo;
import org.example.domain.plainObject.TodoPojo;

import java.util.List;

public interface IToDoService {
    TodoPojo createTodo(Todo todo, Long userId);
    TodoPojo getTodo(Long id, Long userId);
    TodoPojo updateTodo(Todo updatedTodo, Long todoId, Long userId);
    String deleteTodo(Long id, Long userId);
    List<TodoPojo> getAllTodos(Long userId);
}
