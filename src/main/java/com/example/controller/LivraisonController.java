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

import com.example.model.Livraison;
import com.example.services.LivraisonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/livraisons")
public class LivraisonController {

    @Autowired
    private LivraisonService livraisonService;

    @Autowired
    private ObjectMapper objectMapper;

    // GET all
    @GetMapping
    public List<String> getAll() {
        List<Livraison> livraisons = livraisonService.getAll();
        return livraisons.stream()
                .map(livraison -> {
                    try {
                        return objectMapper.writeValueAsString(livraison);
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
            String jsonData = objectMapper.writeValueAsString(livraisonService.getByID(id));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error converting to JSON", HttpStatus.NOT_FOUND);
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Livraison livraison) {
        try {
            livraisonService.insert(livraison);
            String jsonData = objectMapper.writeValueAsString("Livraison ajoutée");
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return new ResponseEntity<>(jsonData, headers, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Not Created", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody Livraison livraison, @PathVariable("id") int id) {
        try {
            Livraison existingLivraison = livraisonService.getByID(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

            if (existingLivraison == null) {
                return new ResponseEntity<>("{\"error\": \"Livraison not found\"}", headers, HttpStatus.NOT_FOUND);
            }

            existingLivraison.setDate(livraison.getDate());
            existingLivraison.setDepart(livraison.getDepart());
            existingLivraison.setEtat(livraison.getEtat());
            existingLivraison.setLivreur(livraison.getLivreur());
            existingLivraison.setColis(livraison.getColis());

            livraisonService.update(existingLivraison);
            String jsonData = objectMapper.writeValueAsString("Livraison modifiée");
            return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Not Updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        try {
            livraisonService.delete(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return new ResponseEntity<>("{\"message\": \"Livraison supprimée\"}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
