package com.study.stream.service;

import com.study.stream.channel.SpringStreamChannel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(SpringStreamChannel.class)
public class StreamService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SpringStreamChannel channel;

    public void produce(String message) {
        System.out.println("发送消息");
        channel.producer().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(SpringStreamChannel.CONSUMER)
    public void consume(String message) {
        System.out.println("接收消息 " + message);
    }

}
