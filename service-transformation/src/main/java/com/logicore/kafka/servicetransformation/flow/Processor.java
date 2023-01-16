package com.logicore.kafka.servicetransformation.flow;

import com.logicore.kafka.servicetransformation.paymenttransform.Transform;
import model.payment.ActionStatus;
import model.payment.PaymentMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class Processor {

    Transform transformationEngine;
    private Parser parser;
    private PaymentMessage paymentMessage;
    private String topic;
    public Processor(Parser parser, PaymentMessage paymentMessage) {
        this.parser = parser;
        this.paymentMessage = paymentMessage;
    }

    private LinkedHashMap<String, String> getEntryPoint() {
        LinkedHashMap<String, String> ruleName = parser.entryPoints.get(topic);
        return ruleName;
    }

    private void ruleEntryProcessor(LinkedHashMap<String, String> ruleNameEntry) throws IOException, InterruptedException, URISyntaxException {
        String jarTransform = ruleNameEntry.get("Transform");
        String endPoint = ruleNameEntry.get("EndPoint");

        paymentMessage = transformationEngine.transformPaymentMessage(jarTransform, paymentMessage);
        paymentMessage.getMessageProcessStatus().setActionStatus(ActionStatus.KAFKA);
        paymentMessage.getMessageProcessStatus().setStatus(ruleNameEntry.get("Status"));
        paymentMessage.getMessageProcessStatus().setTopic(ruleNameEntry.get("EndPoint"));
    }

    public PaymentMessage processLogic() throws IOException, InterruptedException, URISyntaxException {
        log.debug("FlowProcessor: Enter into main process method");
        LinkedHashMap<String, String> ruleNameEntry = getEntryPoint();

        Map<String, Object> actionMap = new HashMap<>();

        Boolean externalProcesed = paymentMessage.getMessageProcessStatus().getExternalProcesed();

        if (ruleNameEntry != null) {
            //Process the EntryPoint
            log.debug("FlowProcessor: Enter into entryPoint processor {}", ruleNameEntry);
            ruleEntryProcessor(ruleNameEntry);

            String endPoint = ruleNameEntry.get("EndPoint");
            actionMap.put("topic", endPoint);
        }
        //Set externalProcess on false
        paymentMessage.getMessageProcessStatus().setExternalProcesed(false);

        return paymentMessage;
    }


}


