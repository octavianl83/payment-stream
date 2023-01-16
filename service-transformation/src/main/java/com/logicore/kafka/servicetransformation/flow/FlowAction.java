package com.logicore.kafka.servicetransformation.flow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import com.logicore.kafka.servicetransformation.paymenttransform.Transform;
import model.payment.PaymentMessage;

public class FlowAction {

    PaymentMessage paymentMessage;
    String topic;
    private Selector selector;

    public FlowAction(PaymentMessage paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public PaymentMessage process() throws IOException, InterruptedException, URISyntaxException {
        String tenantId = paymentMessage.getTenant().getTenantId();

        //Load flow specific to the tennantId
        HashMap<String, Object> flowHashMap = selector.loadFlow(tenantId);

        //Map flow into an object
        Parser parser = new Parser(flowHashMap);
        Processor processor = new Processor(parser, paymentMessage);
        return processor.processLogic();
    }

}
