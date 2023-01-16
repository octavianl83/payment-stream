package com.logicore.kafka.simulatorprocessor.flow;

import model.payment.PaymentMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FlowAction {

    PaymentMessage paymentMessage;
    String topic;
    public FlowAction(PaymentMessage paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public PaymentMessage process() throws IOException {
        String tenantId = paymentMessage.getTenant().getTenantId();

        //Load flow specific to the tennantId
        Selector selector = new Selector(tenantId);
        HashMap<String, Object> flowHashMap = selector.loadFlow();

        //Map flow into an object
        Parser parser = new Parser(flowHashMap);
        Processor processor = new Processor(parser, paymentMessage);
        return processor.processLogic();
    }

}
