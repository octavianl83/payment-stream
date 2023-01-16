package com.logicore.kafka.serviceflowprocessor.transform;


import com.logicore.kafka.serviceflowprocessor.flow.FlowAction;
import model.payment.PaymentMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Transform {

    public PaymentMessage getTransformedMessage(PaymentMessage paymentMessage) {

        FlowAction flowAction = new FlowAction(paymentMessage);
        try {
            return flowAction.process();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
