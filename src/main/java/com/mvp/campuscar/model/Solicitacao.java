package com.mvp.campuscar.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Solicitacao {

    private Long id;

    @NotNull(message = "O ID da carona é obrigatório")
    private Long caronaId;

    @NotNull(message = "O ID do passageiro é obrigatório")
    private Long passageiroId;

    @NotBlank
    private String status; // PENDENTE, ACEITA, RECUSADA

    // Construtores, Getters e Setters omitidos por brevidade
}