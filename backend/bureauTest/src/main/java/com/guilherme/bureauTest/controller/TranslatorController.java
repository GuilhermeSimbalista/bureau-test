package com.guilherme.bureauTest.controller;

import com.guilherme.bureauTest.dto.PageResponse;
import com.guilherme.bureauTest.entity.Translator;
import com.guilherme.bureauTest.service.TranslatorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/translators")
public class TranslatorController {

    private final TranslatorService translatorService;

    public TranslatorController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping
    public PageResponse<Translator> getAllTranslators(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        return translatorService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Translator> getTranslatorById(@PathVariable Long id) {
        Optional<Translator> translator = translatorService.findById(id);
        if (translator.isPresent()) {
            return ResponseEntity.ok(translator.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Translator createTranslator(@RequestBody Translator translator) {
        return translatorService.save(translator);
    }

    @PostMapping("/upload")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file) {
        return translatorService.saveTranslatorsFromCSV(file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Translator> updateTranslator(@PathVariable Long id, @RequestBody Translator updatedTranslator) {
        Translator translator = translatorService.updateTranslator(id, updatedTranslator);
        if (translator != null) {
            return ResponseEntity.ok(translator);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranslator(@PathVariable Long id) {
        boolean deleted = translatorService.deleteTranslator(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
