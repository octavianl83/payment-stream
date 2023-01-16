package com.logicore.kafka.serviceflowprocessor.flow;

import com.logicore.kafka.serviceflowprocessor.ruleengine.RuleenginePaymentMessageService;
import model.payment.ActionStatus;
import model.payment.PaymentMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Processor {

    private Parser parser;
    private PaymentMessage paymentMessage;
    private String topic;
    public Processor(Parser parser, PaymentMessage paymentMessage) {
        this.parser = parser;
        this.paymentMessage = paymentMessage;
    }

    private String getEntryPoint() {
        String ruleName = parser.entryPoints.get(topic);
        return ruleName;
    }

    private Map<String, String> getTask() {
        String messageStatus = paymentMessage.getMessageProcessStatus().getStatus();
        Map<String, String> taskDetails = parser.tasks.get(messageStatus);
        return taskDetails;
    }

    private String getExitPoint() {
        String messageStatus = paymentMessage.getMessageProcessStatus().getStatus();
        String kafkaTopic = parser.exitPoints.get(messageStatus);
        return kafkaTopic;
    }

    private void ruleEntryProcessor(String ruleNameEntry) {
        paymentMessage.getMessageProcessStatus().setRuleName(ruleNameEntry);
        paymentMessage.getMessageProcessStatus().setActionStatus(ActionStatus.RULEENGINE);
    }

    private void ruleExitProcessor(String kafkaTopic) {
        paymentMessage.getMessageProcessStatus().setTopic(kafkaTopic);
        paymentMessage.getMessageProcessStatus().setActionStatus(ActionStatus.KAFKA);
    }

    private void taskProcessor(Map<String, String> task) {
        String ruleName = task.get("RuleSet");
//        String commitOnComplete = task.get("CommitOnComplete");
        RuleenginePaymentMessageService ruleEngine = new RuleenginePaymentMessageService();
        try {
            paymentMessage = ruleEngine.processPaymentMessage(paymentMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        paymentMessage.getMessageProcessStatus().setRuleName(ruleName);
    }

    public PaymentMessage processLogic() {
        log.info("FlowProcessor: Enter into main process method");
        //First we check if there are tasks to be processed
        Map<String, String> task = getTask();

        while (task != null) {
            //Process the task
            log.info("FlowProcessor: Enter into task processor {}", task);
            taskProcessor(task);
            task = getTask();
        }

        Map<String, Object> actionMap = new HashMap<>();
        String ruleNameExit = getExitPoint();
        if (ruleNameExit != null) {
            //Process the ExitPoint
            log.info("FlowProcessor: Enter into exitPoint processor {}", ruleNameExit);
            ruleExitProcessor(ruleNameExit);
        } else {
            log.info("FlowProcessor: Unknown step in flow processing");
        }

        return paymentMessage;
    }


}


