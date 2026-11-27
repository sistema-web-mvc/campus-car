package com.mvp.campuscar.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.mvp.campuscar.model.Solicitacao;
import com.mvp.campuscar.repository.SolicitacaoRepository;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    public void criarSolicitacao(Solicitacao solicitacao) {
        if (solicitacaoRepository.existsByCaronaIdAndPassageiroId(
                solicitacao.getCaronaId(), solicitacao.getPassageiroId())) {
            throw new IllegalStateException("Este passageiro já solicitou esta carona.");
        }

        solicitacao.setStatus("PENDENTE");

        try {
            solicitacaoRepository.save(solicitacao);
        } catch (DuplicateKeyException e) {
            throw new IllegalStateException("Este passageiro já solicitou esta carona.", e);
        }
    }
}