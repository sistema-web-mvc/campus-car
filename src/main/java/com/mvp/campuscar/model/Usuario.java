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

    // Construtores, Getters e Setters omitidos por brevidade
}
