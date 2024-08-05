package com.guilherme.bureauTest.config;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.Reader;

@Component
public class OpenCSVConfig {

    public <T> CsvToBean<T> csvToBean(Reader reader, Class<T> type) {
        return new CsvToBeanBuilder<T>(reader)
                .withType(type)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }
}
