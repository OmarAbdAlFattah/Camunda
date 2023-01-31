//package com.example.workflow.validator;
//
//import com.example.workflow.exception.ProcessException;
//import com.example.workflow.process.ProcessContext;
//import com.example.workflow.validator.results.ValidationResults;
//
//import java.util.Optional;
//
//public abstract class AbstractValidation extends AbstractProcessComponent implements ValidationComponent<String>{
//    public static final String CONTEXT_OBJ_VALIDATION_RESULTS = "VALIDATION_RESULTS";
//
//    private ContinuationPolicy continuationPolicy;
//
//    private Integer weight;
//
//    @Override
//    public final void process(ProcessContext<String> processContext) throws ProcessException {
//        ValidationResults validateResults = validate(processContext);
//
//        AbstractValidation.addValidationResultsToContext(processContext, validateResults);
//
//        checkContinuationPolicy(this, validateResults);
//    }
//
//    protected static void checkContinuationPolicy(AbstractValidation validation, ValidationResults validateResults)
//            throws InterruptedProcessExecutionException {
//        if (!ContinuationPolicy.canContinue(validation.getContinuationPolicy(), validateResults.getStatus())) {
//            throw new InterruptedProcessExecutionException(
//                    "Interrupted the process execution based on the validation continuation policy for the validator id [ "
//                            + validation.getId() + " ]");
//        }
//    }
//
//    protected static void addValidationResultsToContext(ProcessContext<String> processContext,
//                                                        ValidationResults validateResults) {
//        Optional<ContextObject<?>> validationResults = processContext.getContextObject(CONTEXT_OBJ_VALIDATION_RESULTS);
//
//        if (!validationResults.isPresent()) {
//            processContext.putContextObject(CONTEXT_OBJ_VALIDATION_RESULTS, new ContextObject<Object>(validateResults));
//        } else {
//            ValidationResults existValidationResults = (ValidationResults) validationResults.get().getValue();
//
//            aggregateValidationResults(existValidationResults, validateResults);
//        }
//    }
//
//    protected static void aggregateValidationResults(ValidationResults aggregatedValidationResults, ValidationResults validateResults) {
//        aggregatedValidationResults.addInfoMessages(validateResults.getInfoMessages());
//        aggregatedValidationResults.addWarningMessages(validateResults.getWarningMessages());
//        aggregatedValidationResults.addErrorMessages(validateResults.getErrorMessages());
//    }
//}
