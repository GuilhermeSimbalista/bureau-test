package com.guilherme.bureauTest.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslatorCSV {

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "source_language")
    private String sourceLanguage;

    @CsvBindByName(column = "target_language")
    private String targetLanguage;
}
