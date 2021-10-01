package org.example.services;

import org.example.domain.User;
import org.example.services.inrerfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * by Iskortsev S.V.
 */

@Service
public class UserService implements IUserService {


    @Autowired
    public UserService( ) {

    }


    @Override
    public int createUser(User user) {
        return 0;
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    public int updateUser(User user, long id) {
        return 0;
    }

    @Override
    public int deleteUser(long id) {
        return 0;
    }
}
