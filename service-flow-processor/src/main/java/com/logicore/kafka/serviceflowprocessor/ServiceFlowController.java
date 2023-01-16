package com.logicore.kafka.serviceflowprocessor;


import com.logicore.kafka.serviceflowprocessor.flow.FlowAction;
import com.logicore.kafka.serviceflowprocessor.flow.Selector;
import com.logicore.kafka.serviceflowprocessor.services.KafkaService;
import model.payment.PaymentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class ServiceFlowController {

    @Autowired
    private KafkaService kafkaService;

    private String ruleengineTopic = "ruleengine";

    @Autowired
//    @Qualifier("localFlow")
    @Qualifier("DBFlow")
    private Selector selector;

    @PostMapping("/flow-processor")
    public void processFlow(@RequestBody PaymentMessage paymentMessage) throws Exception {

        kafkaService.kafkaSend(paymentMessage, "message11");

    }

}
