package com.mvp.campuscar.repository;

import com.mvp.campuscar.model.Carona;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CaronaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CaronaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Carona> rowMapper = (rs, rowNum) -> {
        Carona carona = new Carona();
        carona.setId(rs.getLong("id"));
        carona.setMotoristaId(rs.getLong("motorista_id"));
        carona.setOrigem(rs.getString("origem"));
        carona.setDestino(rs.getString("destino"));
        carona.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
        carona.setVagasDisponiveis(rs.getInt("vagas_disponiveis"));
        return carona;
    };

    public int save(Carona carona) {
        String sql = "INSERT INTO caronas (motorista_id, origem, destino, data_hora, vagas_disponiveis) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            carona.getMotoristaId(), 
            carona.getOrigem(), 
            carona.getDestino(), 
            carona.getDataHora(), 
            carona.getVagasDisponiveis()
        );
    }

    public List<Carona> findAll() {
        String sql = "SELECT * FROM caronas";
        return jdbcTemplate.query(sql, rowMapper);
    }
}