package com.example.workflow.decode;

import com.example.workflow.exception.ProcessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Base64;
//base64Decoder
@Component
public class InvoiceDecoder implements JavaDelegate {
    public static final String SETTINGS_INPUT_CONTEXT_KEY_ENCODED_VALUE = "INPUT_CONTEXT_KEY_ENCODED_VALUE";
    public static final String SETTINGS_OUTPUT_CONTEXT_KEY_DECODED_VALUE = "OUTPUT_CONTEXT_KEY_DECODED_VALUE";

    private static final Logger log = LogManager.getLogger(InvoiceDecoder.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("DECODER");
        try {
//            ContextObject contextObject = new ContextObject<>("value");
            String encodedValue = (String) delegateExecution.getVariable("encodedInvoice");
            String decodeValue = new String(Base64.getDecoder().decode(encodedValue));
            delegateExecution.setVariable("decodedInvoice",decodeValue);
        }catch(Exception e) {
            log.error(e.getMessage());
            throw new ProcessException("Invalid-Invoice","Invoice-Base64-Decoder-Error",
                    "Invalid encoded base 64 format");
        }
    }
}
