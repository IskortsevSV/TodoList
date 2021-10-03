package org.example.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * by Iskortsev S.V.
 */

@Getter
@Setter
@AllArgsConstructor
public class TokenPayload {

    private Long userId;
    private String email;
    private Date timeOfCreation;

}
