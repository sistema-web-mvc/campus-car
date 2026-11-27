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
import com.mvp.campuscar.service.CaronaService;

import jakarta.validation.Valid;

@Controller
public class CaronaController {

    private final CaronaService caronaService;

    public CaronaController(CaronaService caronaService) {
        this.caronaService = caronaService;
    }

    @GetMapping("/caronas/criar")
    public String formulario(Model model) {
        model.addAttribute("carona", new Carona());
        model.addAttribute("motoristas", caronaService.motoristasDisponiveis());
        return "carona-form";
    }

    @PostMapping("/caronas/criar")
    public String criar(@Valid @ModelAttribute("carona") Carona carona, BindingResult result, Model model) {
        List<Usuario> motoristas = caronaService.motoristasDisponiveis();
        try {
            caronaService.validarMotoristaDisponivel(carona.getMotoristaId());
        } catch (IllegalStateException e) {
            result.rejectValue("motoristaId", "carona.motoristaId.indisponivel", e.getMessage());
        }

        model.addAttribute("motoristas", motoristas);
        if (result.hasErrors()) {
            return "carona-form";
        }

        caronaService.criarCarona(carona);
        return "redirect:/caronas";
    }
}
