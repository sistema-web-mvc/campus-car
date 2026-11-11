package com.mvp.campuscar.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Usuario {
    
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 10, max = 20)
    private String telefone;

    private boolean isMotorista;

    // Construtor vazio (necessário para o RowMapper e frameworks)
    public Usuario() {
    }

    // Construtor com todos os parâmetros (opcional, mas útil)
    public Usuario(Long id, String nome, String email, String telefone, boolean isMotorista) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.isMotorista = isMotorista;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isMotorista() {
        return isMotorista;
    }

    public void setMotorista(boolean motorista) {
        isMotorista = motorista;
    }
}