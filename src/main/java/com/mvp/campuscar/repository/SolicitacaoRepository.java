package com.mvp.campuscar.repository;

import com.mvp.campuscar.model.Carona;
import com.mvp.campuscar.model.Solicitacao;
import com.mvp.campuscar.model.Usuario;
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

        Carona carona = new Carona();
        carona.setId(solicitacao.getCaronaId());
        carona.setOrigem(rs.getString("carona_origem"));
        carona.setDestino(rs.getString("carona_destino"));
        carona.setDisponibilidade(rs.getString("carona_disponibilidade"));
        carona.setVagasDisponiveis(rs.getInt("carona_vagas"));

        Usuario motorista = new Usuario();
        motorista.setId(rs.getLong("motorista_id"));
        motorista.setNome(rs.getString("motorista_nome"));
        carona.setMotorista(motorista);
        solicitacao.setCarona(carona);

        Usuario passageiro = new Usuario();
        passageiro.setId(solicitacao.getPassageiroId());
        passageiro.setNome(rs.getString("passageiro_nome"));
        solicitacao.setPassageiro(passageiro);
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
        String sql = "SELECT s.id, s.carona_id, s.passageiro_id, s.status, " +
                "c.origem AS carona_origem, c.destino AS carona_destino, " +
                "c.disponibilidade AS carona_disponibilidade, c.vagas_disponiveis AS carona_vagas, " +
                "c.motorista_id, m.nome AS motorista_nome, p.nome AS passageiro_nome " +
                "FROM solicitacoes s " +
                "JOIN caronas c ON s.carona_id = c.id " +
                "JOIN usuarios m ON c.motorista_id = m.id " +
                "JOIN usuarios p ON s.passageiro_id = p.id";
        return jdbcTemplate.query(sql, rowMapper);
    }
}