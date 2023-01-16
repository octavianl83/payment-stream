package com.logicore.kafka.simulatorprocessor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.kafka.simulatorprocessor.bindings.KafkaListenerBinding;
import com.logicore.kafka.simulatorprocessor.flow.Selector;
import com.logicore.kafka.simulatorprocessor.transform.StreamProcess;
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
public class SimulatorProcessorService {

    @Autowired
    @Qualifier("localFlow")
//    @Qualifier("DBFlow")
    private Selector selector;

    @Autowired
    StreamProcess streamProcess;

    @Autowired
    ObjectMapper objectMapper;

    @StreamListener("input-channel-1")
    public void process(KStream<String, PaymentMessage> input) {

        log.info("Receive a message in stream");
        input.peek((k, v) -> log.info("We have a message in general stream: {} {}", k, v));

        KStream<String, PaymentMessage> inputProcessed = input.mapValues(v -> streamProcess.getTransformedMessage(v));

        inputProcessed.to("messageprocessed");
    }
}
