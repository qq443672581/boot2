package cn.dlj1.boot2.activeMQ.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @JmsListener(destination = "test")
    public void receiveMsg(String text) {
        System.out.println("<<<<<<============ 收到消息： " + text);
    }
}
