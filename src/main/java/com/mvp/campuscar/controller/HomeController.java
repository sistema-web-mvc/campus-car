package com.mvp.campuscar.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mvp.campuscar.repository.CaronaRepository;

@Controller
public class HomeController {

    private final CaronaRepository caronaRepository;

    public HomeController(CaronaRepository caronaRepository) {
        this.caronaRepository = caronaRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
   
        return "base"; // Renderiza templates/index.html
    }

    @GetMapping("/caronas")
    public String caronas(Model model) {
        model.addAttribute("caronas", caronaRepository.findAll());
        return "caronas"; // leva até a listagem de caronas
    }
}