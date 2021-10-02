package org.example.utils;

import org.example.domain.Tag;
import org.example.domain.ToDo;
import org.example.domain.User;
import org.example.domain.plainObject.TagPojo;
import org.springframework.stereotype.Component;
import org.example.domain.plainObject.UserPojo;
import org.example.domain.plainObject.ToDoPojo;

import java.util.stream.Collectors;

/**
 * by Iskortsev S.V.
 */

@Component
public class Converter {

    public UserPojo userToPojo(User source) {
        UserPojo result = new UserPojo();

        result.setId(source.getId());
        result.setUsername(source.getUsername());
        result.setPassword(source.getPassword());

        result.setTodoList(source.getToDoSet().stream().map(this::todoToPojo).collect(Collectors.toSet()));

        return result;
    }

    public ToDoPojo todoToPojo(ToDo source) {

        ToDoPojo result = new ToDoPojo();

        result.setId(source.getId());
        result.setName(source.getName());
        result.setComment(source.getComment());
        result.setStartDate(source.getStartDate());
        result.setEndDate(source.getEndDate());
        result.setImportant(source.getImportant());
        result.setPriority(source.getPriority());
        result.setUserId(source.getUser().getId());

        result.setTagList(source.getTagList().stream().map(this::tagToPojo).collect(Collectors.toSet()));

        return result;
    }

    public TagPojo tagToPojo (Tag source) {
        TagPojo result = new TagPojo();

        result.setId(source.getId());
        result.setName(source.getName());
        result.setTodoListIds(source.getTodoList().stream().map(ToDo::getId).collect(Collectors.toSet()));

        return result;
    }
}
