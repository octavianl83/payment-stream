package com.logicore.kafka.servicetransformation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import model.payment.PaymentMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentMessageService {

    @Autowired
    ObjectMapper objectMapper;

    public PaymentMessage transformPaymentMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        PaymentMessage paymentMessage = objectMapper.readValue(consumerRecord.value(), PaymentMessage.class);
        log.info("payment message: {}", paymentMessage);
        save(paymentMessage);
        return paymentMessage;
    }

    private void save(PaymentMessage paymentMessage) {
        log.debug("Succesfully persisted the payment message {}", paymentMessage);

    }


}
