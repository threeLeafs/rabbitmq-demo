package com.example.demo.mapper;


import com.example.demo.pojo.MsgLog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MsgLogMapper {

    void insert(MsgLog msgLog);

    void updateStatus(MsgLog msgLog);

    List<MsgLog> selectTimeoutMsg();

    void updateTryCount(MsgLog msgLog);

    MsgLog selectByPrimaryKey(String msgId);

}
