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

import com.example.model.Utilisateur;
import com.example.services.UtilisateurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ObjectMapper objectMapper;

    // GET all
    @GetMapping
    public List<String> getAll() {
        List<Utilisateur> utilisateurs = utilisateurService.getAll();
        return utilisateurs.stream()
                .map(utilisateur -> {
                    try {
                        return objectMapper.writeValueAsString(utilisateur);
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
            String jsonData = objectMapper.writeValueAsString(utilisateurService.getByID(id));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error converting to JSON", HttpStatus.NOT_FOUND);
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Utilisateur utilisateur) {
        try {
            utilisateurService.insert(utilisateur);
            String jsonData = objectMapper.writeValueAsString("Utilisateur ajouté");
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return new ResponseEntity<>(jsonData, headers, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Not Created", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody Utilisateur utilisateur, @PathVariable("id") int id) {
        try {
            Utilisateur existingUtilisateur = utilisateurService.getByID(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

            if (existingUtilisateur == null) {
                return new ResponseEntity<>("{\"error\": \"Utilisateur not found\"}", headers, HttpStatus.NOT_FOUND);
            }

            if (utilisateur.getNom() != null) existingUtilisateur.setNom(utilisateur.getNom());
            if (utilisateur.getEmail() != null) existingUtilisateur.setEmail(utilisateur.getEmail());
            // Retirer cette ligne car 'motDePasse' n'existe pas
            // if (utilisateur.getMotDePasse() != null) existingUtilisateur.setMotDePasse(utilisateur.getMotDePasse());

            utilisateurService.update(existingUtilisateur);
            String jsonData = objectMapper.writeValueAsString("Utilisateur modifié");
            return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Not Updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        try {
            utilisateurService.delete(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return new ResponseEntity<>("{\"message\": \"Utilisateur supprimé\"}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
