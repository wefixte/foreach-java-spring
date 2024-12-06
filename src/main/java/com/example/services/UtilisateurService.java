package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.model.Utilisateur;
import com.example.wrappers.UtilisateurWrapper;

@Service
public class UtilisateurService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UtilisateurWrapper utilisateurWrapper;

    public List<Utilisateur> getAll() {
        String sql = "SELECT * FROM utilisateurs";
        return jdbcTemplate.query(sql, this.utilisateurWrapper);
    }

    public Utilisateur getByID(int id) {
        String sql = "SELECT * FROM utilisateurs WHERE Id = ?";
        return jdbcTemplate.queryForObject(sql, this.utilisateurWrapper, id);
    }

    public int insert(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (Nom, Email) VALUES (?,?)";
        return jdbcTemplate.update(sql, utilisateur.getNom(), utilisateur.getEmail());
    }

    public int update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET Nom=?, Email=? WHERE Id=?";
        return jdbcTemplate.update(sql, utilisateur.getNom(), utilisateur.getEmail(), utilisateur.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM utilisateurs WHERE Id=?";
        return jdbcTemplate.update(sql, id);
    }
}
