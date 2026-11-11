package com.mvp.campuscar.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mvp.campuscar.model.Usuario;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setTelefone(rs.getString("telefone"));
        usuario.setMotorista(rs.getBoolean("is_motorista"));
        return usuario;
    };

    public int save(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            usuario.getNome(), 
            usuario.getEmail(), 
            usuario.getTelefone(), 
            usuario.isMotorista()
        );
    }

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios";
        return jdbcTemplate.query(sql, rowMapper);
    }
}