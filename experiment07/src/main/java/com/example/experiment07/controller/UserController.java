package com.example.experiment07.controller;

import com.example.experiment07.component.EncryptorComponent;
import com.example.experiment07.entity.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private Map<String, User> users = new HashMap();

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EncryptorComponent encryptorComponent;

    @PostMapping("/register")
    public Map register(@RequestBody User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       users.put(user.getUserName(), user);
       return Map.of("user",user);
    }

    @PostMapping("/login")
    //HttpServletResponse对象代表服务器的响应。 这个对象中封装了向客户端发送数据、发送响应头，发送响应状态码的方法
    public void login(@RequestBody User user,HttpServletResponse httpServletResponse){
        Optional.ofNullable(users.get(user.getUserName()))
                .or(()->{
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"用户名或密码错误");
                })
                .ifPresent(u->{
                    if(!passwordEncoder.matches(user.getPassword(), u.getPassword())){
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"用户名或密码错误");
                    }
                    Map map = Map.of("name",u.getUserName());
                    //生成加密token
                    String token = encryptorComponent.encrypt(map);
                    //向客户端发送响应头
                    httpServletResponse.setHeader("name", token);
                }

                );
    }
    //注解@RequestAttribute可以被用于访问由过滤器或拦截器创建的、
    // 预先存在的请求属性
    @GetMapping("/index")
    public Map getIndex(@RequestAttribute String name11){
        log.debug(name11);
        return Map.of("name",name11);
    }

}
