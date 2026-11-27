package com.mvp.campuscar.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mvp.campuscar.model.Carona;
import com.mvp.campuscar.model.Usuario;
import com.mvp.campuscar.repository.CaronaRepository;
import com.mvp.campuscar.repository.UsuarioRepository;

@Service
public class CaronaService {

    private static final String MOTORISTA_INDISPONIVEL = "Selecione um motorista disponível.";

    private final CaronaRepository caronaRepository;
    private final UsuarioRepository usuarioRepository;

    public CaronaService(CaronaRepository caronaRepository, UsuarioRepository usuarioRepository) {
        this.caronaRepository = caronaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> motoristasDisponiveis() {
        return usuarioRepository.findAll().stream()
                .filter(Usuario::isMotorista)
                .toList();
    }

    public void validarMotoristaDisponivel(Long motoristaId) {
        if (motoristaId != null && motoristasDisponiveis().stream()
                .noneMatch(motorista -> Objects.equals(motorista.getId(), motoristaId))) {
            throw new IllegalStateException(MOTORISTA_INDISPONIVEL);
        }
    }

    public void criarCarona(Carona carona) {
        caronaRepository.save(carona);
    }
}
