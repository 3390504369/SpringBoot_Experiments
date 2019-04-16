package com.example.experiment05.controller;

import com.example.experiment05.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
//声明为支持REST的控制组件，基于Jackson自动完成，将java对象序列化为
//json返回，以及将传入的json反序列化为java对象
@RequestMapping("/api")
//声明此组件所有处理地址的相对路径
public class UserController {

    private static List<User> users = create();
    private static List<User> create() {
        users = new ArrayList<>();
        User user = new User(1, "zk", "123456");
        User user1 = new User(2, "hrs", "333333");
        User user2 = new User(3, "flj", "444444");
        users.add(user);
        users.add(user1);
        users.add(user2);
        return users;
    }

    @GetMapping("/index")
    public Map getIndex() {
        return Map.of("index", "main");
    }
//                                 创建map数组

    @GetMapping("/users")
    public Map getUsers() {
        return Map.of("users", users);
    }

    @GetMapping("/users/{uid}")
    public Map getUser(@PathVariable int uid) {
        log.debug("{}", uid);
        User user = users.stream()
                .filter(u -> u.getId() == uid)
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(user)
                .map(u -> Map.of("user", u))
                .orElse(Map.of());
    }

    @PostMapping("/users")
    public Map postUser(@RequestBody User user) {
        //              反序列化为java对象
        users.add(user);
        return Map.of("users", user);
    }


}
