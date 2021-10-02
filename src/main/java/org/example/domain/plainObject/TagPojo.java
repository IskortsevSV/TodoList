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
public class TagPojo {

    private Long id;
    private String name;
    private Set<Long> todoListIds = new HashSet<>();

}
