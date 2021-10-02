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
@EqualsAndHashCode(of = {"username","password"})
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
    private Set<ToDo> toDoSet = new HashSet<>();

    public Set<ToDo> getToDoSet() {
        return toDoSet;
    }


    //перегруженный метод
    public void addTodo(ToDo todo) {
        addTodo(todo,false);
    }

    // добавили задачу нашему пользователю , добавляем булево значение чтобы убрать рекурсию
    public void addTodo(ToDo toDo, boolean otherSideHasBeenSet) {
        this.getToDoSet().add(toDo);
        if(otherSideHasBeenSet){
            return;
        }
        toDo.setUser(this);
    }

    //перегруженный метод
    public void removeTodo(ToDo todo) {
        removeTodo(todo,false);
    }

    // добавили задачу нашему пользователю , добавляем булево значение чтобы убрать рекурсию
    public void removeTodo(ToDo todo, boolean otherSideHasBeenSet) {
        this.getToDoSet().remove(todo);
        if(otherSideHasBeenSet) {
            return;
        }
        todo.removeUser(this, true);
    }
}
