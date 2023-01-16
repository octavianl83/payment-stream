package com.logicore.kafka.servicevalidation.bindings;


public interface KafkaListenerBinding {

    @Input("input-channel-1")
    KStream<String, PaymentMessage> inputStream1();

}
