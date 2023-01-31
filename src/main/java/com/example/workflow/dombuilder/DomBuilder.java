package com.example.workflow.dombuilder;

import com.example.workflow.exception.ProcessException;
import com.example.workflow.process.ContextObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

@Component
public class DomBuilder implements JavaDelegate {

    private static final Logger LOGGER = LogManager.getLogger(DomBuilder.class);
    public static final  String SETTINGS_INPUT_CONTEXT_KEY_INVOICE = "invoice";
    public static final  String SETTINGS_OUTPUT_CONTEXT_KEY_INVOICE_DOM = "OUTPUT_CONTEXT_KEY_INVOICE_DOM";
    public static final  String SETTINGS_OUTPUT_CONTEXT_KEY_XPATH = "OUTPUT_CONTEXT_KEY_XPATH";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("DOM BUILDER");
        try {
            //String invoiceContextKey = (String) delegateExecution.getVariable("decodedInvoice");
            String invoice = (String) delegateExecution.getVariable("decodedInvoice");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(invoice));
            Document document = documentBuilder.parse(inputSource);
            delegateExecution.setVariable("doc", document);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            delegateExecution.setVariable("xpath", xpath.toString());
        } catch (SAXException | IOException e) {
            LOGGER.error("Exception : {} : " , e.getMessage());
            throw new ProcessException("XSD_ZATCA_INVALID", "XSD validation",
                    "Schema validation failed; XML does not comply with UBL 2.1 standards in line with ZATCA specifications");
        } catch (ParserConfigurationException e) {
            LOGGER.error("Exception : {} " , e);
            throw new ProcessException("XSD_ZATCA_INVALID", "XSD validation",
                    "Schema validation failed; XML does not comply with UBL 2.1 standards in line with ZATCA specifications");
        }
    }
}
