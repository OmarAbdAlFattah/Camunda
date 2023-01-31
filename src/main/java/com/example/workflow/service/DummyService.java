package com.example.workflow.service;

import com.example.workflow.entity.DummyEntity;
import com.example.workflow.repo.DummyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DummyService {
    @Autowired
    private DummyRepo dummyRepo;



    public void addMessage(DummyEntity message) {
        dummyRepo.save(message);
    }

    public Optional<String> getErrorMessage(String errorCode) {
        return dummyRepo.findByErrorCode(errorCode);
    }
}
