package com.logicore.kafka.servicetransformation.transform;


import com.logicore.kafka.servicetransformation.flow.FlowAction;
import model.payment.PaymentMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class StreamProcess {

    public PaymentMessage getTransformedMessage(PaymentMessage paymentMessage) {

        FlowAction flowAction = new FlowAction(paymentMessage);
        try {
            return flowAction.process();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
