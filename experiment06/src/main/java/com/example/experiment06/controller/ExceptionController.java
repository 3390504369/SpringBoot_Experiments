package com.example.experiment06.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleValidExecption(MethodArgumentNotValidException e){
       StringBuilder stringBuilder = new StringBuilder();
       //值可变非线程安全
        e.getBindingResult().
                getFieldErrors().forEach(
                        e1->{
                            stringBuilder.append(e1.getField()+":");
                            stringBuilder.append(e1.getDefaultMessage()+";");
                        }

        );
        return Map.of("errorMessafe:",stringBuilder.toString());

    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus
    public Map handleInvalidFormatException(InvalidFormatException e){
        StringBuilder stringBuilder = new StringBuilder();
        e.getPath().forEach(e1->{
            stringBuilder.append("属性"+e1.getFieldName());
            stringBuilder.append(",您输入的值："+e.getValue());
            stringBuilder.append(".类型错误");
        });
        return Map.of("message",stringBuilder);
    }

    @ResponseStatus
    @ExceptionHandler(ConstraintViolationException.class)
    public Map handleConstraintViolationException(ConstraintViolationException e){
        StringBuilder stringBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> voilations = e.getConstraintViolations();
        voilations.forEach(v->{
            stringBuilder.append(v.getMessage()+";");
        });
        return Map.of("message",stringBuilder);
    }
}
