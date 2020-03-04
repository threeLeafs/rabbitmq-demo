package com.example.demo.mq.consumer;

import com.example.demo.common.Constant;
import com.example.demo.config.RabbitConfig;
import com.example.demo.mq.MessageHelper;
import com.example.demo.pojo.LoginLog;
import com.example.demo.pojo.Mail;
import com.example.demo.pojo.MsgLog;
import com.example.demo.service.LoginLogService;
import com.example.demo.service.MsgLogService;
import com.example.demo.util.MailUtil;
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
public class SimpleLoginLogConsumer {

    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private MsgLogService msgLogService;

    //@RabbitListener(queues = RabbitConfig.LOGIN_LOG_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        log.info("收到消息: {}", message.toString());
        LoginLog loginLog = MessageHelper.msgToObj(message, LoginLog.class);

        String msgId=loginLog.getMsgId();

        MsgLog msgLog = msgLogService.selectByMsgId(msgId);
        if (null == msgLog || msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)) {// 消费幂等性
            log.info("重复消费, msgId: {}", msgId);
            return;
        }
        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();
        try{
            loginLogService.insert(loginLog);
            msgLogService.updateStatus(msgId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
            channel.basicAck(tag, false);// 消费确认
        }catch(Exception e){
            channel.basicNack(tag, false, true);
        }




    }
}
