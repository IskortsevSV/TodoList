package org.example.services;

import org.example.domain.User;
import org.example.domain.plainObject.UserPojo;
import org.example.exceptions.CustomEmptyDataException;
import org.example.repositories.UserRepository;
import org.example.services.inrerfaces.IUserService;
import org.example.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * by Iskortsev S.V.
 */

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final Converter converter;

    @Autowired
    public UserService(UserRepository userRepository, Converter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public UserPojo createUser(User user) {

        userRepository.save(user);

        return converter.userToPojo(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPojo getUser(long id) {

        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isPresent()) {
            return converter.userToPojo(foundUser.get());
        } else {
            throw new CustomEmptyDataException("unable to get user");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPojo> getAllUsers() {
        List<User> listOfUsers = userRepository.findAll();
        return listOfUsers.stream().map(converter::userToPojo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserPojo updateUser(User source, long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User target = userOptional.get();
            target.setUsername(source.getUsername());
            target.setPassword(source.getPassword());
            userRepository.save(target);
            return converter.userToPojo(target);
        } else
             throw new CustomEmptyDataException("unable to update user");
    }

    @Override
    @Transactional
    public String deleteUser(long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return "User with id: " + id + " was successfully remover";
        } else
            throw new CustomEmptyDataException("unable to delete user");
    }
}
