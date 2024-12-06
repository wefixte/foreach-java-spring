package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Colis;
import com.example.services.ColisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/colis")
public class ColisController {

    @Autowired
    private ColisService colisService;

    @Autowired
    private ObjectMapper objectMapper;

    // GET all
    @GetMapping
    public List<String> getAll() {
        List<Colis> colisList = colisService.getAll();
        return colisList.stream()
                .map(colis -> {
                    try {
                        return objectMapper.writeValueAsString(colis);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error converting to JSON", e);
                    }
                })
                .collect(Collectors.toList());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable("id") int id) {
        try {
            String jsonData = objectMapper.writeValueAsString(colisService.getByID(id));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error converting to JSON", HttpStatus.NOT_FOUND);
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Colis colis) {
        try {
            if (colis.getUtilisateur() == null || colis.getUtilisateur().getId() <= 0) {
                return new ResponseEntity<>("Utilisateur invalide", HttpStatus.BAD_REQUEST);
            }

            colisService.insert(colis);
            String jsonData = objectMapper.writeValueAsString("Colis ajouté");
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return new ResponseEntity<>(jsonData, headers, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Not Created", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody Colis colis, @PathVariable("id") int id) {
        try {
            Colis existingColis = colisService.getByID(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

            if (existingColis == null) {
                return new ResponseEntity<>("{\"error\": \"Colis not found\"}", headers, HttpStatus.NOT_FOUND);
            }

            if (colis.getContenu() != null) existingColis.setContenu(colis.getContenu());
            if (colis.getAdresse() != null) existingColis.setAdresse(colis.getAdresse());
            if (colis.getCodePostal() != null) existingColis.setCodePostal(colis.getCodePostal());
            if (colis.getVille() != null) existingColis.setVille(colis.getVille());
            if (colis.getPays() != null) existingColis.setPays(colis.getPays());

            colisService.update(existingColis);
            String jsonData = objectMapper.writeValueAsString("Colis modifié");
            return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Not Updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        try {
            colisService.delete(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return new ResponseEntity<>("{\"message\": \"Colis supprimé\"}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
