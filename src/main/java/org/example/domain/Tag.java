package org.example.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * by Iskortsev S.V.
 */

@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@ToString(of = {"name","todoList"})
@NoArgsConstructor
@Entity
@Table(name = "TAG")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "tagList")
    private final Set<ToDo> todoList = new HashSet<>();

    public Set<ToDo> getTodoList() {
        return todoList;
    }

    public void addTodo(ToDo todo) {
        addTodo(todo, false);
    }

    public void addTodo(ToDo todo, boolean otherSideHasBeenSet) {
        this.getTodoList().add(todo);
        if(otherSideHasBeenSet) {
            return;
        }
        todo.addTag(this, true);
    }

    public void removeTodo(ToDo todo) {
        removeTodo(todo, false);
    }

    public void removeTodo(ToDo todo, boolean otherSideHasBeenSet) {
        this.getTodoList().remove(todo);
        if(otherSideHasBeenSet) {
            return;
        }
        todo.removeTag(this, true);
    }

}