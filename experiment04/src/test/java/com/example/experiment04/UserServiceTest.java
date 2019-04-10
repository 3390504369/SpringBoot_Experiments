package com.example.experiment04;

import com.example.experiment04.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//运行器，指定SpringRunner来运行
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void buyCarTest() {
        userService.buyCar();
    }

    @Test
    public void addUserTest() {
        userService.addUser();
    }
}
