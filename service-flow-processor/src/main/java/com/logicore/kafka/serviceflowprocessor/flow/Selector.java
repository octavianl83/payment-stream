package com.logicore.kafka.serviceflowprocessor.flow;

import java.io.IOException;
import java.util.HashMap;

public interface Selector {
    HashMap<String, Object> loadFlow(String tenantId) throws IOException;
    void clearCacheFlow(String tenantId);
}
