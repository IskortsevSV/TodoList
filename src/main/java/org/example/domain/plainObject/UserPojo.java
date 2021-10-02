package org.example.domain.plainObject;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * by Iskortsev S.V.
 */
@Getter
@Setter
public class UserPojo {

    private Long id;
    private String username;
    private String password;
    private Set<TodoPojo> todoList = new HashSet<>();

}
