package com.guilherme.bureauTest.service;

import com.guilherme.bureauTest.config.OpenCSVConfig;
import com.guilherme.bureauTest.dto.DocumentCSV;
import com.guilherme.bureauTest.dto.PageResponse;
import com.guilherme.bureauTest.entity.Document;
import com.guilherme.bureauTest.entity.Translator;
import com.guilherme.bureauTest.repository.DocumentRepository;
import com.guilherme.bureauTest.repository.TranslatorRepository;
import com.opencsv.bean.CsvToBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;

    private final TranslatorRepository translatorRepository;

    private final OpenCSVConfig openCSVConfig;

    private final OpenAIClient openAIClient;

    public DocumentService(DocumentRepository documentRepository, TranslatorRepository translatorRepository, OpenCSVConfig openCSVConfig, OpenAIClient openAIClient) {
        this.documentRepository = documentRepository;
        this.translatorRepository = translatorRepository;
        this.openCSVConfig = openCSVConfig;
        this.openAIClient = openAIClient;
    }

    /**
     * Retrieves a page of documents based on the provided Pageable object and returns a PageResponse.
     *
     * @param pageable the Pageable object containing pagination information.
     * @return a PageResponse containing the list of documents on the page, the current page number, and the total number of pages.
     */
    public PageResponse<Document> findAll(Pageable pageable) {
        Page<Document> page = documentRepository.findAll(pageable);
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getTotalPages());
    }

    /**
     * Finds and returns a document by its ID.
     *
     * @param id the ID of the document to be found.
     * @return an Optional containing the found document, or empty if not found.
     */
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    /**
     * Saves a new document to the repository, detecting its locale and assigning a translator if necessary.
     *
     * @param document the document to be saved.
     * @return the saved document.
     */
    public Document save(Document document) {
        if (document.getLocale() == null || document.getLocale().isEmpty()) {
            Mono<String> localeMono = openAIClient.detectLocale(document.getContent());
            document.setLocale(localeMono.block());
        }

        Optional<Translator> translator = translatorRepository.findByEmail(document.getAuthor());
        Translator translatorData = translator.orElseThrow(() -> new RuntimeException("Translator not found for email: " + document.getAuthor()));
        document.setTranslator(translatorData);

        return documentRepository.save(document);
    }

    /**
     * Updates an existing document based on the provided ID and new information.
     *
     * @param id the ID of the document to be updated.
     * @param updatedDocument the new information for the document.
     * @return the updated document, or null if the document is not found.
     */
    public Document updateDocument(Long id, Document updatedDocument) {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            document.setSubject(updatedDocument.getSubject());
            document.setContent(updatedDocument.getContent());
            document.setLocale(updatedDocument.getLocale());
            document.setAuthor(updatedDocument.getAuthor());
            document.setTranslator(updatedDocument.getTranslator());
            return documentRepository.save(document);
        } else {
            return null;
        }
    }

    /**
     * Deletes an existing document based on the provided ID.
     *
     * @param id the ID of the document to be deleted.
     * @return true if the document was successfully deleted, false if the document was not found.
     */
    public boolean deleteDocument(Long id) {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            documentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves multiple documents from a CSV file.
     *
     * @param file the CSV file containing the documents to be saved.
     * @return a message indicating success or failure of the operation.
     */
    public String saveDocumentsFromCSV(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<DocumentCSV> csvToBean = openCSVConfig.csvToBean(reader, DocumentCSV.class);
            List<DocumentCSV> documentsCSV = csvToBean.parse();

            for (DocumentCSV documentCSV : documentsCSV) {
                logger.info("Valores CSV: {}", documentCSV.getSubject());
                Document document = new Document();
                document.setSubject(documentCSV.getSubject());
                document.setContent(documentCSV.getContent());
                document.setAuthor(documentCSV.getAuthor());

                if (documentCSV.getLocale() == null || documentCSV.getLocale().isEmpty()) {
                    Mono<String> localeMono = openAIClient.detectLocale(documentCSV.getContent());
                    document.setLocale(localeMono.block());
                } else {
                    document.setLocale(documentCSV.getLocale());
                }

                Optional<Translator> translator = translatorRepository.findByEmail(documentCSV.getAuthor());
                document.setTranslator(translator.orElseThrow());

                documentRepository.save(document);
            }

            return "Documentos salvos com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao salvar documentos: " + e.getMessage();
        }
    }
}

