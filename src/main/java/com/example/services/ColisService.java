package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.model.Colis;
import com.example.wrappers.ColisWrapper;

@Service
public class ColisService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ColisWrapper colisWrapper;

    public List<Colis> getAll() {
        String sql = "SELECT * FROM colis";
        return jdbcTemplate.query(sql, this.colisWrapper);
    }

    public Colis getByID(int id) {
        String sql = "SELECT * FROM colis WHERE Id = ?";
        return jdbcTemplate.queryForObject(sql, this.colisWrapper, id);
    }

    public int insert(Colis colis) {
        if (colis.getUtilisateur() == null || colis.getUtilisateur().getId() <= 0) {
            throw new IllegalArgumentException("Utilisateur valide requis");
        }
    
        String checkUserSql = "SELECT COUNT(*) FROM utilisateurs WHERE Id = ?";
        Integer userExists = jdbcTemplate.queryForObject(checkUserSql, Integer.class, colis.getUtilisateur().getId());
    
        if (userExists == null || userExists == 0) {
            throw new IllegalArgumentException("Utilisateur non trouvé dans la base de données");
        }
    
        String sql = "INSERT INTO colis (Contenu, Adresse, CodePostal, Ville, Pays, FK_Utilisateur) VALUES (?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, colis.getContenu(), colis.getAdresse(), colis.getCodePostal(), colis.getVille(), colis.getPays(), colis.getUtilisateur().getId());
    }

    public int update(Colis colis) {
        String sql = "UPDATE colis SET Contenu=?, Adresse=?, CodePostal=?, Ville=?, Pays=?, FK_Utilisateur=? WHERE Id=?";
        return jdbcTemplate.update(sql, colis.getContenu(), colis.getAdresse(), colis.getCodePostal(), colis.getVille(), colis.getPays(), colis.getUtilisateur().getId(), colis.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM colis WHERE Id=?";
        return jdbcTemplate.update(sql, id);
    }
}
