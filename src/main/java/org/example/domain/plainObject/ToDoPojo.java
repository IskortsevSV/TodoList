package org.example.domain.plainObject;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Priority;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * by Iskortsev S.V.
 */

@Getter
@Setter
public class ToDoPojo {

    private Long id;
    private String name;
    private String comment;
    private Date startDate;
    private Date endDate;
    private Boolean important;
    private Priority priority;
    private Set<TagPojo> tagList = new HashSet<>();
    private Long userId;

}
