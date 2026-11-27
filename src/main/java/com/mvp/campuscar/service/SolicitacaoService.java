package com.mvp.campuscar.service;

import java.util.Map;
import java.util.Objects;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.mvp.campuscar.model.Carona;
import com.mvp.campuscar.model.Solicitacao;
import com.mvp.campuscar.model.Usuario;
import com.mvp.campuscar.repository.CaronaRepository;
import com.mvp.campuscar.repository.SolicitacaoRepository;
import com.mvp.campuscar.repository.UsuarioRepository;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final CaronaRepository caronaRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository,
            CaronaRepository caronaRepository,
            UsuarioRepository usuarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.caronaRepository = caronaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Map<String, String> criarSolicitacao(Solicitacao solicitacao) {
        if (solicitacao.getCaronaId() == null) {
            return Map.of("caronaId", "Selecione uma carona.");
        }

        if (solicitacao.getPassageiroId() == null) {
            return Map.of("passageiroId", "Selecione um usuário.");
        }

        Carona carona = caronaRepository.findAll().stream()
                .filter(c -> Objects.equals(c.getId(), solicitacao.getCaronaId()))
                .findFirst()
                .orElse(null);
        if (carona == null) {
            return Map.of("caronaId", "Selecione uma carona cadastrada.");
        }

        boolean usuarioExiste = usuarioRepository.findAll().stream()
                .anyMatch(usuario -> Objects.equals(usuario.getId(), solicitacao.getPassageiroId()));
        if (!usuarioExiste) {
            return Map.of("passageiroId", "Selecione um usuário cadastrado.");
        }

        if (Objects.equals(carona.getMotoristaId(), solicitacao.getPassageiroId())) {
            return Map.of("caronaId", "Você não pode solicitar sua própria carona.");
        }

        if (solicitacaoRepository.existsByCaronaIdAndPassageiroId(
                solicitacao.getCaronaId(), solicitacao.getPassageiroId())) {
            return Map.of("caronaId", "Este usuário já solicitou esta carona.");
        }

        solicitacao.setStatus("PENDENTE");

        try {
            solicitacaoRepository.save(solicitacao);
        } catch (DuplicateKeyException e) {
            return Map.of("caronaId", "Este usuário já solicitou esta carona.");
        }

        return Map.of();
    }
}