package com.example.experiment04.service;

import com.example.experiment04.aspect.MyInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
//如果一个类带了@Service注解，将自动注册到Spring容器
@Service
@MyInterceptor
public class UserService {

    public void buyCar() {
        log.debug("buyCar()");
    }
    //默认是user权限

    @MyInterceptor(MyInterceptor.AuthorityType.ADMIN)
    //声明管理员权限
    public void addUser() {
        log.debug("addUser()");
    }
}

