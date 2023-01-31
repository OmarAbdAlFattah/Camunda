//package com.example.workflow;
//
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.JavaDelegate;
//
//public class AddClass implements JavaDelegate {
//    @Override
//    public void execute(DelegateExecution delegateExecution) throws Exception {
//        String x = (String) delegateExecution.getVariable("firstNumber");
//        String y = (String) delegateExecution.getVariable("secondNumber");
//
//        String sum = x + y;
//
//        delegateExecution.setVariable("sum", sum);
//
//    }
//}
