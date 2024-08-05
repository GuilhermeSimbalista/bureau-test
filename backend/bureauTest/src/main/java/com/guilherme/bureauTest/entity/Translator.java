package com.guilherme.bureauTest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Translator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String sourceLanguage;
    private String targetLanguage;

    @JsonIgnore
    @OneToMany(mappedBy = "translator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;



}
