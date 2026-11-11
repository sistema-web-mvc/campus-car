package com.mvp.campuscar.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Solicitacao {

    private Long id;

    @NotNull(message = "O ID da carona é obrigatório")
    private Long caronaId;

    @NotNull(message = "O ID do passageiro é obrigatório")
    private Long passageiroId;

    @NotBlank(message = "O status é obrigatório")
    private String status; // PENDENTE, ACEITA, RECUSADA

    public Solicitacao() {
    }

    public Solicitacao(Long id, Long caronaId, Long passageiroId, String status) {
        this.id = id;
        this.caronaId = caronaId;
        this.passageiroId = passageiroId;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaronaId() {
        return caronaId;
    }

    public void setCaronaId(Long caronaId) {
        this.caronaId = caronaId;
    }

    public Long getPassageiroId() {
        return passageiroId;
    }

    public void setPassageiroId(Long passageiroId) {
        this.passageiroId = passageiroId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}