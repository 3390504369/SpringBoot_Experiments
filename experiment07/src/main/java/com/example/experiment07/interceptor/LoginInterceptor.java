package com.example.experiment07.interceptor;

import com.example.experiment07.component.EncryptorComponent;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private EncryptorComponent encryptorComponent;
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler)throws Exception{
        Optional.ofNullable(request.getHeader("name11"))
                .ifPresentOrElse(token->{
                    var map = encryptorComponent.decrypt(token);
                    request.setAttribute("name11",map.get("name"));
                },()->{
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"未登录");
                        }
                );
        return true;
    }
}
