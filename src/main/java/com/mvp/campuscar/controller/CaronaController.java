package com.mvp.campuscar.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mvp.campuscar.model.Carona;
import com.mvp.campuscar.model.Usuario;
import com.mvp.campuscar.repository.CaronaRepository;
import com.mvp.campuscar.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
public class CaronaController {

    private final CaronaRepository caronaRepository;
    private final UsuarioRepository usuarioRepository;

    public CaronaController(CaronaRepository caronaRepository, UsuarioRepository usuarioRepository) {
        this.caronaRepository = caronaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/caronas/criar")
    public String formulario(Model model) {
        model.addAttribute("carona", new Carona());
        model.addAttribute("motoristas", motoristasDisponiveis());
        return "carona-form";
    }

    @PostMapping("/caronas/criar")
    public String criar(@Valid @ModelAttribute("carona") Carona carona, BindingResult result, Model model) {
        List<Usuario> motoristas = motoristasDisponiveis();
        if (carona.getMotoristaId() != null
                && motoristas.stream().noneMatch(motorista -> motorista.getId().equals(carona.getMotoristaId()))) {
            result.rejectValue("motoristaId", "carona.motoristaId.indisponivel",
                    "Selecione um motorista disponível.");
        }

        model.addAttribute("motoristas", motoristas);
        if (result.hasErrors()) {
            return "carona-form";
        }

        caronaRepository.save(carona);
        return "redirect:/caronas";
    }

    private List<Usuario> motoristasDisponiveis() {
        return usuarioRepository.findAll().stream()
                .filter(Usuario::isMotorista)
                .toList();
    }
}
