package com.example.workflow.controller;

import com.example.workflow.entity.DummyEntity;
import com.example.workflow.service.DummyService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DummyController  {

    @Autowired
    DummyService dummyService;

    @PostMapping("/add")
    public void addDummy(@RequestBody DummyEntity message){
        dummyService.addMessage(message);
    }

    @GetMapping(path = "/get/{errorcode}")
    public ResponseEntity<String> getErrorMessage(@PathVariable("errorcode") String errorcode) {
        String errorMessage = String.valueOf(dummyService.getErrorMessage(errorcode));
        System.out.println("THE GOOD ERROR MESSAGE IS -> " + errorMessage);
        return new ResponseEntity<> (errorMessage, HttpStatus.OK);
    }
}
