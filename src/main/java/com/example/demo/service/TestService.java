package com.example.demo.service;


import com.example.demo.common.ServerResponse;
import com.example.demo.pojo.Mail;

public interface TestService {

    ServerResponse testIdempotence();

    ServerResponse accessLimit();

    ServerResponse send(Mail mail);
}
