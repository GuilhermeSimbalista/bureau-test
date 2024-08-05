package com.guilherme.bureauTest.service;

import com.guilherme.bureauTest.config.OpenCSVConfig;
import com.guilherme.bureauTest.dto.PageResponse;
import com.guilherme.bureauTest.dto.TranslatorCSV;
import com.guilherme.bureauTest.entity.Translator;
import com.guilherme.bureauTest.repository.TranslatorRepository;
import com.opencsv.bean.CsvToBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public class TranslatorService {

    private final TranslatorRepository translatorRepository;

    private final OpenCSVConfig openCSVConfig;

    public TranslatorService(TranslatorRepository translatorRepository, OpenCSVConfig openCSVConfig) {
        this.translatorRepository = translatorRepository;
        this.openCSVConfig = openCSVConfig;
    }

    /**
     * Retrieves a page of translators based on the provided Pageable object and returns a PageResponse.
     *
     * @param pageable the Pageable object containing pagination information.
     * @return a PageResponse containing the list of translators on the page, the current page number, and the total number of pages.
     */
    public PageResponse<Translator> findAll(Pageable pageable) {
        Page<Translator> page = translatorRepository.findAll(pageable);
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getTotalPages());
    }

    /**
     * Finds and returns a translator by its ID.
     *
     * @param id the ID of the translator to be found.
     * @return an Optional containing the found translator, or empty if not found.
     */
    public Optional<Translator> findById(Long id) {
        return translatorRepository.findById(id);
    }

    /**
     * Saves a new translator to the repository.
     *
     * @param translator the translator to be saved.
     * @return the saved translator.
     */
    public Translator save(Translator translator) {
        return translatorRepository.save(translator);
    }

    /**
     * Updates an existing translator based on the provided ID and new information.
     *
     * @param id the ID of the translator to be updated.
     * @param updatedTranslator the new information for the translator.
     * @return the updated translator, or null if the translator is not found.
     */
    public Translator updateTranslator(Long id, Translator updatedTranslator) {
        Optional<Translator> optionalTranslator = translatorRepository.findById(id);
        if (optionalTranslator.isPresent()) {
            Translator translator = optionalTranslator.get();
            translator.setName(updatedTranslator.getName());
            translator.setEmail(updatedTranslator.getEmail());
            translator.setSourceLanguage(updatedTranslator.getSourceLanguage());
            translator.setTargetLanguage(updatedTranslator.getTargetLanguage());
            return translatorRepository.save(translator);
        } else {
            return null;
        }
    }

    /**
     * Deletes an existing translator based on the provided ID.
     *
     * @param id the ID of the translator to be deleted.
     * @return true if the translator was successfully deleted, false if the translator was not found.
     */
    public boolean deleteTranslator(Long id) {
        Optional<Translator> optionalTranslator = translatorRepository.findById(id);
        if (optionalTranslator.isPresent()) {
            translatorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves multiple translators from a CSV file.
     *
     * @param file the CSV file containing the translators to be saved.
     * @return a message indicating success or failure of the operation.
     */
    public String saveTranslatorsFromCSV(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<TranslatorCSV> csvToBean = openCSVConfig.csvToBean(reader, TranslatorCSV.class);
            List<TranslatorCSV> translatorsCSV = csvToBean.parse();
            for (TranslatorCSV translatorCSV : translatorsCSV) {
                Translator translator = new Translator();
                translator.setName(translatorCSV.getName());
                translator.setEmail(translatorCSV.getEmail());
                translator.setSourceLanguage(translatorCSV.getSourceLanguage());
                translator.setTargetLanguage(translatorCSV.getTargetLanguage());
                translatorRepository.save(translator);
            }

            return "File uploaded successfully.";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "An error occurred while processing the file.";
        }
    }
}
