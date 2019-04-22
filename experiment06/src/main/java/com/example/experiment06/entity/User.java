package com.example.experiment06.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class User {
    @Size(min = 2,max = 6,message = "用户名{min}-{max}位")
    private String userName;
    @Size(min = 6,message = "最小个数{min}")
    private String password;
    @Max(value = 60,message = "年龄最大{value}")
    @Min(value = 18,message = "年龄最小{value}")
    private int age;
    @Email
    //根据e-email的正则表达式来验证输入
    private String email;
}
