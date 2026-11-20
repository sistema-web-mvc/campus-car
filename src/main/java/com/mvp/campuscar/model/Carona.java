package com.mvp.campuscar.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Carona {

    private Long id;

    @NotNull(message = "O ID do motorista é obrigatório")
    private Long motoristaId;

    @NotBlank(message = "A origem é obrigatória")
    private String origem;

    @NotBlank(message = "O destino é obrigatório")
    private String destino;

    @NotBlank(message = "Descreva sua disponibilidade")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String disponibilidade;

    @NotNull(message = "O número de vagas é obrigatório")
    @Min(value = 1, message = "Deve haver pelo menos 1 vaga disponível")
    private Integer vagasDisponiveis;

    private Usuario motorista;

    public Carona() {
    }

    public Carona(Long id, Long motoristaId, String origem, String destino, String disponibilidade, Integer vagasDisponiveis) {
        this.id = id;
        this.motoristaId = motoristaId;
        this.origem = origem;
        this.destino = destino;
        this.disponibilidade = disponibilidade;
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

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(Integer vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Usuario getMotorista() {
        return motorista;
    }

    public void setMotorista(Usuario motorista) {
        this.motorista = motorista;
    }
}