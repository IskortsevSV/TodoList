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
@EqualsAndHashCode(of = {"id","username","password"})
@ToString(of = {"username","password","toDoSet"})
@NoArgsConstructor
@Entity
@Table(name = "_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false, length = 3000)
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Todo> todoSet = new HashSet<>();

    public Set<Todo> getTodoSet() {
        return todoSet;
    }


    //перегруженный метод
    public void addTodo(Todo todo) {
        addTodo(todo,false);
    }

    // добавили задачу нашему пользователю , добавляем булево значение чтобы убрать рекурсию
    public void addTodo(Todo toDo, boolean otherSideHasBeenSet) {
        this.getTodoSet().add(toDo);
        if(otherSideHasBeenSet){
            return;
        }
        toDo.setUser(this,true);
    }

    //перегруженный метод
    public void removeTodo(Todo todo) {
        removeTodo(todo,false);
    }

    // добавили задачу нашему пользователю , добавляем булево значение чтобы убрать рекурсию
    public void removeTodo(Todo todo, boolean otherSideHasBeenSet) {
        this.getTodoSet().remove(todo);
        if(otherSideHasBeenSet) {
            return;
        }
        todo.removeUser(this, true);
    }
}
