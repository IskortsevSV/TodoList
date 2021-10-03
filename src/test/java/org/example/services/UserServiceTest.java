package org.example.services;

import org.example.domain.User;
import org.example.domain.plainObject.UserPojo;
import org.example.services.inrerfaces.IUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.Assert.*;


/**
 * by Iskortsev S.V.
 */



public class UserServiceTest {

    private final String USERNAME = "user";
    private final String PASSWORD = "123";

    private IUserService userService;

    @Before
    public void init() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/mainTest.xml");
        this.userService = applicationContext.getBean("userService", IUserService.class);
    }

    @After
    public void cleanDB() {
        userService.getAllUsers().forEach(userPojo -> userService.deleteUser(userPojo.getId()));
    }

    @Test
    public void createUserTest() {
        User newUser = new User();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);

        assertEquals(USERNAME, actual.getUsername());
        assertEquals(PASSWORD, actual.getPassword());
        assertNotNull(actual.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUniqueUserEmail() {
        User newUser = new User();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);
        userService.createUser(newUser);
        User newUser2 = new User();
        newUser2.setUsername(USERNAME);
        newUser2.setPassword(PASSWORD);
        userService.createUser(newUser2);
    }

    @Test
    public void getUserTest() {
        User newUser = new User();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);
        UserPojo current = userService.getUser(actual.getId());

        assertEquals(actual.getId(), current.getId());
        assertEquals(USERNAME, current.getUsername());
        assertEquals(PASSWORD, current.getPassword());

    }

    @Test
    public void findUserByEmailAndPasswordTest() {
        User newUser = new User();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);
        UserPojo current = userService.findUserByUsernameAndPassword(USERNAME, PASSWORD);

        assertEquals(actual.getId(), current.getId());
        assertEquals(actual.getUsername(), current.getUsername());
        assertEquals(actual.getPassword(), current.getPassword());

    }

    @Test
    public void updateUserTest() {
        User newUser = new User();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);
        UserPojo actual = userService.createUser(newUser);

        User userForUpdate = new User();
        userForUpdate.setUsername("123");
        userForUpdate.setPassword("111");

        UserPojo updatedUser = userService.updateUser(userForUpdate, actual.getId());

        assertEquals(actual.getId(), updatedUser.getId());
        assertEquals("123", updatedUser.getUsername());
        assertEquals("111", updatedUser.getPassword());



    }

    @Test
    public void deleteUserTest() {
        User newUser = new User();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);

        List<UserPojo> userListAfterCreate = userService.getAllUsers();
        assertEquals(1, userListAfterCreate.size());

        userService.deleteUser(actual.getId());

        List<UserPojo> userListAfterDelete = userService.getAllUsers();
        assertEquals(0, userListAfterDelete.size());
    }

}
