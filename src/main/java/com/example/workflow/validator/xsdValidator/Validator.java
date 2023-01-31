package com.example.workflow.validator.xsdValidator;

import com.example.workflow.validator.results.ValidationResults;
import com.example.workflow.validator.results.ValidationResultsImpl;
import com.example.workflow.validator.singelton.ValidatorSingleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
@Component
public class Validator implements JavaDelegate  {

    private static final Logger LOGGER = LogManager.getLogger(Validator.class);

    public static final String SETTINGS_INPUT_CONTEXT_KEY_INVOICE = "INPUT_CONTEXT_KEY_INVOICE";
    public static final String SETTINGS_INPUT_INPUT_CONTEXT_KEY_XSD_FILE = "INPUT_CONTEXT_KEY_XSD_FILE";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ValidationResults results =new ValidationResultsImpl();
        ByteArrayInputStream byteArray = null;
//       src/main/resources/xsd/UBL-Invoice.xsd

        try {
            String invoice = (String) delegateExecution.getVariable("decoded-Invoice");
            String xsdFilePath = (String) delegateExecution.getVariable("xsdFilePath");
//            File xsdFile = new File(xsdFilePath.toUri());

            ValidatorSingleton.setXsdFile(Path.of(xsdFilePath));
            ValidatorSingleton.setValidatorSingleton();
            javax.xml.validation.Validator validator =ValidatorSingleton.getValidator();

            synchronized (validator){
                LOGGER.info(invoice);
                byteArray=new ByteArrayInputStream(invoice.getBytes());
                validator.validate(new StreamSource(byteArray));

                results.addInfoMessage("XSD_ZATCA_VALID", "XSD validation",
                        "Complied with UBL 2.1 standards in line with ZATCA specifications");
                byteArray.close();
                delegateExecution.setVariable("validationResult", results);
                LOGGER.info("VALIDATION RESULTS >>> ", results);
            }
        } 	catch (Exception e) {
            LOGGER.error("Schema validation Exception : {} :" , e.getMessage());
            e.printStackTrace(); // For Testing
            results.addErrorMessage("XSD_ZATCA_INVALID", "XSD validation",
                    "Schema validation failed; XML does not comply with UBL 2.1 standards in line with ZATCA specifications");
        }

    }
}
