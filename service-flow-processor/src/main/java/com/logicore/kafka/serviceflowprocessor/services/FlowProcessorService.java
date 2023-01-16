package com.logicore.kafka.serviceflowprocessor.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.kafka.serviceflowprocessor.bindings.KafkaListenerBinding;
import com.logicore.kafka.serviceflowprocessor.flow.Selector;
import lombok.extern.slf4j.Slf4j;
import model.payment.PaymentMessage;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import com.logicore.kafka.serviceflowprocessor.transform.Transform;

@Service
@Slf4j
@EnableBinding(KafkaListenerBinding.class)
public class FlowProcessorService {

    @Autowired
    @Qualifier("localFlow")
//    @Qualifier("DBFlow")
    private Selector selector;

    @Autowired
    Transform transformFunction;

    @Autowired
    ObjectMapper objectMapper;

    @StreamListener("input-channel-1")
    public void process(KStream<String, PaymentMessage> input) {

        log.info("Receive a message in stream");
        input.peek((k, v) -> log.info("We have a message in general stream: {} {}", k, v));

        KStream<String, PaymentMessage> inputProcessed = input.map((k,v) -> new KeyValue<>(v.getTransactionId(), transformFunction.getTransformedMessage(v)));

        KStream<String, PaymentMessage> inputTransform = inputProcessed.
                filter((k,v) -> v.getMessageProcessStatus().getTopic().equalsIgnoreCase("transform")).
                peek((k, v) -> log.info("We have a message in transform stream: {} {}", k, v));

        KStream<String, PaymentMessage> inputSimulator = inputProcessed.
                filter((k,v) -> v.getMessageProcessStatus().getTopic().equalsIgnoreCase("simulator")).
                peek((k, v) -> log.info("We have a message in simulator stream: {} {}", k, v));

        KStream<String, PaymentMessage> inputValidator = inputProcessed.
                filter((k,v) -> v.getMessageProcessStatus().getTopic().equalsIgnoreCase("validator")).
                peek((k, v) -> log.info("We have a message in validation stream: {} {}", k, v));

        inputTransform.mapValues(v -> {
            try {
                return objectMapper.writeValueAsString(v);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).to("transform");

        inputValidator.mapValues(v -> {
            try {
                return objectMapper.writeValueAsString(v);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).to("validator");

        inputTransform.mapValues(v -> {
            try {
                return objectMapper.writeValueAsString(v);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).to("simulator");

    }
}
