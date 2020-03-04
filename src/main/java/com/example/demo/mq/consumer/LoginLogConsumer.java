package com.example.demo.mq.consumer;

import com.example.demo.common.Constant;
import com.example.demo.config.RabbitConfig;
import com.example.demo.mq.BaseConsumer;
import com.example.demo.mq.MessageHelper;
import com.example.demo.pojo.LoginLog;
import com.example.demo.pojo.MsgLog;
import com.example.demo.service.LoginLogService;
import com.example.demo.service.MsgLogService;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoginLogConsumer implements BaseConsumer {

    @Autowired
    private LoginLogService loginLogService;

    @Override
    public void consume(Message message, Channel channel) {
        log.info("收到消息: {}", message.toString());
        LoginLog loginLog = MessageHelper.msgToObj(message, LoginLog.class);
        loginLogService.insert(loginLog);
    }
}
