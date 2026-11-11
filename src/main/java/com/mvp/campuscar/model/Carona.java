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

    @NotNull(message = "O número de vagas é obrigatório")
    @Min(value = 1, message = "Deve haver pelo menos 1 vaga disponível")
    private Integer vagasDisponiveis;

    public Carona() {
    }

    public Carona(Long id, Long motoristaId, String origem, String destino, LocalDateTime dataHora, Integer vagasDisponiveis) {
        this.id = id;
        this.motoristaId = motoristaId;
        this.origem = origem;
        this.destino = destino;
        this.dataHora = dataHora;
        this.vagasDisponiveis = vagasDisponiveis;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(Long motoristaId) {
        this.motoristaId = motoristaId;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(Integer vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }
}