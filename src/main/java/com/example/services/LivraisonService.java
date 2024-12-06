package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.model.Livraison;
import com.example.wrappers.LivraisonWrapper;

@Service
public class LivraisonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LivraisonWrapper livraisonWrapper;

    public List<Livraison> getAll() {
        String sql = "SELECT * FROM livraison";
        return jdbcTemplate.query(sql, this.livraisonWrapper);
    }

    public Livraison getByID(int id) {
        String sql = "SELECT * FROM livraison WHERE Id = ?";
        return jdbcTemplate.queryForObject(sql, this.livraisonWrapper, id);
    }

    public int insert(Livraison livraison) {
        String sql = "INSERT INTO livraison (Date, Depart, Etat, LivreurId) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql, livraison.getDate(), livraison.getDepart(), livraison.getEtat(), livraison.getLivreur().getId());
    }

    public int update(Livraison livraison) {
        String sql = "UPDATE livraison SET Date=?, Depart=?, Etat=?, LivreurId=? WHERE Id=?";
        return jdbcTemplate.update(sql, livraison.getDate(), livraison.getDepart(), livraison.getEtat(), livraison.getLivreur().getId(), livraison.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM livraison WHERE Id=?";
        return jdbcTemplate.update(sql, id);
    }
}
