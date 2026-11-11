package com.mvp.campuscar.repository;

import com.mvp.campuscar.model.Solicitacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SolicitacaoRepository {

    private final JdbcTemplate jdbcTemplate;

    public SolicitacaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Solicitacao> rowMapper = (rs, rowNum) -> {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(rs.getLong("id"));
        solicitacao.setCaronaId(rs.getLong("carona_id"));
        solicitacao.setPassageiroId(rs.getLong("passageiro_id"));
        solicitacao.setStatus(rs.getString("status"));
        return solicitacao;
    };

    public int save(Solicitacao solicitacao) {
        String sql = "INSERT INTO solicitacoes (carona_id, passageiro_id, status) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, 
            solicitacao.getCaronaId(), 
            solicitacao.getPassageiroId(), 
            solicitacao.getStatus()
        );
    }

    public List<Solicitacao> findAll() {
        String sql = "SELECT * FROM solicitacoes";
        return jdbcTemplate.query(sql, rowMapper);
    }
}