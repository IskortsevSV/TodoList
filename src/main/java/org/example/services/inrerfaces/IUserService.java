package org.example.services.inrerfaces;

import org.example.domain.User;
import org.example.domain.plainObject.UserPojo;
import java.util.List;

public interface IUserService {

    UserPojo createUser(User user);
    UserPojo getUser(long id);
    List<UserPojo> getAllUsers();
    UserPojo updateUser(User user, long id);
    String deleteUser(long id);

}
