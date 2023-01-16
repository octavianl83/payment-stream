package com.logicore.kafka.servicevalidation.bindings;


import model.payment.PaymentMessage;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface KafkaListenerBinding {

    @Input("input-channel-1")
    KStream<String, PaymentMessage> inputStream();

}
