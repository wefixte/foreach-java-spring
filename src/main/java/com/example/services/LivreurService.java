package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.model.Livreur;
import com.example.wrappers.LivreurWrapper;

@Service
public class LivreurService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LivreurWrapper livreurWrapper;

    public List<Livreur> getAll() {
        String sql = "SELECT * FROM livreurs";
        return jdbcTemplate.query(sql, this.livreurWrapper);
    }

    public Livreur getByID(int id) {
        String sql = "SELECT * FROM livreurs WHERE Id = ?";
        return jdbcTemplate.queryForObject(sql, this.livreurWrapper, id);
    }

    public int insert(Livreur livreur) {
        String sql = "INSERT INTO livreurs (Nom, Email, Telephone, Disponible) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql, livreur.getNom(), livreur.getEmail(), livreur.getTelephone(), livreur.isDisponible());
    }

    public int update(Livreur livreur) {
        String sql = "UPDATE livreurs SET Nom=?, Email=?, Telephone=?, Disponible=? WHERE Id=?";
        return jdbcTemplate.update(sql, livreur.getNom(), livreur.getEmail(), livreur.getTelephone(), livreur.isDisponible(), livreur.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM livreurs WHERE Id=?";
        return jdbcTemplate.update(sql, id);
    }
}
