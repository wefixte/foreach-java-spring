package com.example.wrappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.model.Colis;
import com.example.services.UtilisateurService;

@Component
public class ColisWrapper implements RowMapper<Colis> {
    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public Colis mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new Colis(
                rs.getInt("Id"),
                rs.getString("Contenu"),
                rs.getString("Adresse"),
                rs.getString("CodePostal"),
                rs.getString("Ville"),
                rs.getString("Pays"),
                utilisateurService.getByID(rs.getInt("FK_Utilisateur"))
        );
    }
}