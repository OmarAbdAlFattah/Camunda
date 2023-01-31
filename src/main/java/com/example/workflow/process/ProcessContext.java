package com.example.workflow.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProcessContext<T> {
    Map<T, ContextObject<? extends Object>> processEntries = new HashMap<>();
    private static final Logger  LOGGER = LogManager.getLogger(ProcessContext.class);

    public Optional<ContextObject<? extends Object>> getContextObject(T key) {
        ContextObject<? extends Object> contextObject = processEntries.get(key);
        if(contextObject == null && !key.equals("CONTEXT_OBJ_VALIDATION_RESULTS")) {
            LOGGER.error("contextObject : key :%s , %s : {}" , key + "value is null");
        }
        return Optional.ofNullable(contextObject);
    }

    public void putContextObject(T key, ContextObject<Object> contextObject) {
        processEntries.put(key, contextObject);
    }

    public void clear() {
        processEntries.clear();
    }
}
