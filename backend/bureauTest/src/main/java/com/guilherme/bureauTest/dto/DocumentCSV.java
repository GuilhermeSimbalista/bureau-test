package com.guilherme.bureauTest.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentCSV {

    @CsvBindByName(column = "subject")
    private String subject;

    @CsvBindByName(column = "content")
    private String content;

    @CsvBindByName(column = "locale", required = false)
    private String locale;

    @CsvBindByName(column = "author")
    private String author;
}
