package com.example.workflow.validator.enschematronValidator;

import camundajar.impl.scala.language;
import com.example.workflow.dto.ErrorDTO;
import com.example.workflow.entity.DummyEntity;
import com.example.workflow.validator.results.ValidationResults;
import com.example.workflow.validator.results.ValidationResultsImpl;
import com.example.workflow.validator.singelton.ENXsltTransformerSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.XdmDestination;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class EnSchemaValidator implements JavaDelegate {
    private static final Logger LOGGER = LogManager.getLogger(EnSchemaValidator.class);
    public static final String SETTINGS_SCHEMATRON_RULES_PATH = "SCHEMATRON_RULES_PATH";
    public static final String SETTINGS_INPUT_CONTEXT_KEY_INVOICE = "INPUT_CONTEXT_KEY_INVOICE";
    public static final String SETTINGS_INPUT_CONTEXT_KEY_LANGUAGE = "INPUT_CONTEXT_KEY_LANGUAGE"; //
    public static final String SETTINGS_BUSINESS_RULES_NAME = "BUSINESS_RULES_NAME";
    public static final String SETTINGS_INPUT_CONTEXT_KEY_REST_TEMPLATE = "INPUT_CONTEXT_KEY_REST_TEMPLATE";
    Map<String,ValidationResults> errorAndWarResults = new HashMap<>();
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ValidationResults results = new ValidationResultsImpl();
//        Environment env = ApplicationContextHolder.getContext().getBean(Environment.class);
        String messageCode="";
        try {
            String invoice = (String) delegateExecution.getVariable("decodedInvoice"); //1
//            String businessRulesName = (String) delegateExecution.getVariable("rulesNames"); //KSA
//            RestTemplate restTemplate = (RestTemplate) delegateExecution.getVariable("restTemplate");
            String language = (String) delegateExecution.getVariable("lang"); //2
            String schematronFile = (String) delegateExecution.getVariable("enXsdFile"); //3

            ENXsltTransformerSingleton.setSchematronFile(Path.of(schematronFile));
            ENXsltTransformerSingleton.xsltLoad();
            XsltTransformer transformer = ENXsltTransformerSingleton.getTransformer();
            LOGGER.info("Start EN validation ******************");

            synchronized (transformer) {
                transformer.setSource(new StreamSource(new ByteArrayInputStream(invoice.getBytes())));
                XdmDestination chainResult = new XdmDestination();
                transformer.setDestination(chainResult);
                transformer.transform();

                XdmNode rootnode = chainResult.getXdmNode();
                String messageFlag = null;
                String message = "";

                for (XdmNode node : rootnode.children().iterator().next().children()) {
                    if (node.getNodeName() == null || !("failed-assert".equals(node.getNodeName().getLocalName()))){
                        continue;
                    }
                    messageCode = node.getAttributeValue(new QName("id"));
                    String messageCategory = "KSA";
                    messageFlag = node.getAttributeValue(new QName("flag"));
                    Map params = new HashMap();
                    params.put("errorCode", messageCode);
                    String errorMessage = node.children().iterator().next().getUnderlyingValue().getStringValue();
                    ErrorDTO errorDTO = new ErrorDTO(messageCode, errorMessage,null,messageFlag);
                    if (language.equalsIgnoreCase("ar") && errorDTO.getArabicMessage() != null && !errorDTO.getArabicMessage().isBlank()){
                        message = errorDTO.getArabicMessage();
                    }
                    else {
                        message = errorDTO.getMessage();
                    }


                    if (messageFlag.equalsIgnoreCase("WARNING")) {
                        if (!errorAndWarResults.containsKey(messageCode)) {
                            results.addWarningMessage(messageCode, messageCategory, message);
                            errorAndWarResults.put(messageCode, results);
                        }
                    } else if (messageFlag.equalsIgnoreCase("INFO")) {
                        results.addInfoMessage(messageCode, messageCategory, message);
                    } else {
                        if (!errorAndWarResults.containsKey(messageCode)) {
                            results.addErrorMessage(messageCode, messageCategory, message);
                            errorAndWarResults.put(messageCode, results);
                        }
                    }
                }
                DummyEntity dummyEntity=new DummyEntity(messageCode, message);
                ObjectWriter ow=new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json=ow.writeValueAsString(dummyEntity);

                delegateExecution.setVariable("output","ll");
                delegateExecution.setVariable("errorAndWarResultsDTO",json);
            }

        } catch (Exception e) {
            LOGGER.error("Error : {} : ",  e.getMessage());
            e.printStackTrace(); // For Test
            results.addErrorMessage("GENERAL", "BUSINESS_RULES",
                    "Unable to execute Business Rules validation ->" + messageCode);
        }
        LOGGER.info("End EN validation ****************** : {}",results);

    }
}

