package com.example.wrappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.model.Livreur;

@Component
public class LivreurWrapper implements RowMapper<Livreur> {

    @Override
    public Livreur mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new Livreur(
                rs.getInt("Id"),
                rs.getString("Nom"),
                rs.getString("Email"),
                rs.getString("Telephone"),
                rs.getInt("Disponible") == 1 // Conversion de l'entièreté de la valeur Disponible en boolean
        );
    }
}
