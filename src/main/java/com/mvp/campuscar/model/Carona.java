package com.mvp.campuscar.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Carona {

    private Long id;

    @NotNull(message = "O ID do motorista é obrigatório")
    private Long motoristaId;

    @NotBlank(message = "A origem é obrigatória")
    private String origem;

    @NotBlank(message = "O destino é obrigatório")
    private String destino;

    @NotNull(message = "A data e hora são obrigatórias")
    @FutureOrPresent(message = "A carona deve ser no presente ou futuro")
    private LocalDateTime dataHora;

    @NotNull
    @Min(value = 1, message = "Deve haver pelo menos 1 vaga disponível")
    private Integer vagasDisponiveis;

    // Construtores, Getters e Setters omitidos por brevidade
}