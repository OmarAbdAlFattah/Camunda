package com.example.workflow.repo;

import com.example.workflow.entity.DummyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DummyRepo extends JpaRepository<DummyEntity, Long> {

    Optional<String> findByErrorCode(String errorCode);
}
