package com.mvp.campuscar.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Campus Car - Home");
        // Aqui você pode injetar repositórios via construtor para buscar as caronas recentes e enviar para a view
        return "index"; // Renderiza templates/index.html
    }
}