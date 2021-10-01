package org.example.services;

import org.example.domain.User;
import org.example.domain.plainObject.UserPojo;
import org.example.services.inrerfaces.IUserService;
import org.example.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * by Iskortsev S.V.
 */

@Service
public class UserService implements IUserService {

    @PersistenceContext
    EntityManager entityManager;

    private final Converter converter;

    @Autowired
    public UserService(Converter converter) {
        this.converter = converter;
    }

    @Override
    @Transactional
    public UserPojo createUser(User user) {

        entityManager.persist(user);

        return converter.userToPojo(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPojo getUser(long id) {

        User foundUser = entityManager
                .createQuery("SELECT user FROM User user WHERE user.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();

        return converter.userToPojo(foundUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPojo> getAllUsers() {
        List<User> listOfUsers= entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();

        List<UserPojo> result = listOfUsers.stream().map(converter::userToPojo).collect(Collectors.toList());
        return result;
    }

    @Override
    public UserPojo updateUser(User updatedUser, long id) {

        return null;
    }

    @Override
    public UserPojo deleteUser(long id) {

        return null;
    }
}
