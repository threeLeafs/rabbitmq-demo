package com.example.demo.mapper;


import com.example.demo.pojo.LoginLog;
import org.springframework.stereotype.Component;

@Component
public interface LoginLogMapper {

    void insert(LoginLog loginLog);

    LoginLog selectByMsgId(String msgId);

}
