package com.guilherme.bureauTest.repository;

import com.guilherme.bureauTest.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
