package com.logicore.kafka.servicevalidation.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.kafka.servicevalidation.bindings.KafkaListenerBinding;
import lombok.extern.slf4j.Slf4j;
import model.payment.PaymentMessage;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableBinding(KafkaListenerBinding.class)
public class ValidationProcessorService {

    @Autowired
    ObjectMapper objectMapper;

    @StreamListener("input-channel-1")
    public void process(KStream<String, PaymentMessage> input) {

        log.info("Receive a message in stream");
        input.peek((k, v) -> log.info("We have a message in general stream: {} {}", k, v));

        input.to("messageprocessed");
    }
}
