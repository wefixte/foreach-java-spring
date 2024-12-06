package com.example.wrappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.model.Colis;
import com.example.model.Livraison;
import com.example.model.Livreur;
import com.example.services.ColisService;
import com.example.services.LivreurService;

@Component
public class LivraisonWrapper implements RowMapper<Livraison> {

    @Autowired
    private ColisService colisService;

    @Autowired
    private LivreurService livreurService;

    @Override
    public Livraison mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Livraison livraison = new Livraison();
        
        livraison.setId(rs.getInt("Id"));
        livraison.setDate(rs.getDate("Date"));
        livraison.setDepart(rs.getString("Depart"));
        livraison.setEtat(rs.getString("Etat"));
        
        int livreurId = rs.getInt("LivreurId");
        int colisId = rs.getInt("ColisId");
        
        Livreur livreur = livreurService.getByID(livreurId);
        Colis colis = colisService.getByID(colisId);
        
        livraison.setLivreur(livreur);
        livraison.setColis(colis);

        return livraison;
    }
}
