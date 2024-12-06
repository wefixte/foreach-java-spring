package com.example.wrappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.model.Utilisateur;

@Component
public class UtilisateurWrapper implements RowMapper<Utilisateur> {

    @Override
    public Utilisateur mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new Utilisateur(
            rs.getInt("Id"),
            rs.getString("Nom"),
            rs.getString("Email")
        );
    }
}
