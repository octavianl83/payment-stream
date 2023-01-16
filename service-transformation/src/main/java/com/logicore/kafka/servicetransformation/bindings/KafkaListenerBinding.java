package com.logicore.kafka.servicetransformation.bindings;

import model.payment.PaymentMessage;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface KafkaListenerBinding {

    @Input("input-channel-1")
    KStream<String, PaymentMessage> inputStream1();

}
