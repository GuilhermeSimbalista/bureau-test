package com.guilherme.bureauTest.repository;

import com.guilherme.bureauTest.entity.Translator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranslatorRepository extends JpaRepository<Translator, Long> {

    Optional<Translator> findByEmail(String email);
}
