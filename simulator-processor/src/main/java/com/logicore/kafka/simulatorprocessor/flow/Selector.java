package com.logicore.kafka.simulatorprocessor.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.payment.PaymentMessage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
@EnableCaching
public class Selector {

    private String tenant = null;

    public Selector(PaymentMessage paymentMessage) {
        this.tenant = paymentMessage.getTenant().getTenantId();
    }

    public Selector(String tenantId) {
        this.tenant = tenantId;
    }

    public String createFlowName(String tenantId) {
        return "simulator-config".concat(".json");
    }

    @Cacheable(value = "flows")
    public HashMap<String, Object> loadFlow() throws IOException {
        String fileName = createFlowName(this.tenant);
        File file = new File(this.getClass().getClassLoader().getResource(fileName).getFile());
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> flowConfig = mapper.readValue(file, HashMap.class);
        return flowConfig;
    }


    public static void main(String[] args) throws IOException {
        Selector flowSelector = new Selector("tenant1");
        HashMap myFlow = flowSelector.loadFlow();

    }
}
