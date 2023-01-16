package com.logicore.kafka.serviceflowprocessor.flow;

import model.payment.PaymentMessage;

import java.io.IOException;
import java.util.HashMap;

public class FlowAction {

    PaymentMessage paymentMessage;
    String topic;
    private Selector selector;

    public FlowAction(PaymentMessage paymentMessage) {
        this.selector = new LocalSelector();
        this.paymentMessage = paymentMessage;
    }

    public PaymentMessage process() throws IOException {
        String tenantId = paymentMessage.getTenant().getTenantId();

        //Load flow specific to the tennantId
        HashMap<String, Object> flowHashMap = selector.loadFlow(tenantId);

        //Map flow into an object
        Parser parser = new Parser(flowHashMap);
        Processor processor = new Processor(parser, paymentMessage);
        return processor.processLogic();
    }

}
