package com.study.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SpringStreamChannel {

    /**
     * 消息消费者
     * @return
     */
    String CONSUMER = "mqSpringStreamConsumer";
    @Input(CONSUMER)
    SubscribableChannel consumer();

    /**
     * 生产者
     */
    String PRODUCER = "mqSpringStreamProducer";
    @Output(PRODUCER)
    MessageChannel producer();
}
