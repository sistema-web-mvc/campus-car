package com.mvp.campuscar.controller;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mvp.campuscar.repository.CaronaRepository;

@Controller
public class HomeController {

    public final CaronaRepository(CaronaRepository caronas);

    @GetMapping("/")
    public String index(Model model) {
   
        return "index"; // Renderiza templates/index.html
    }

    @GetMapping ("/caronas")
    public String caronas(Model model) {
        
 

        return "caronas"; // leva até a listagem de caronas
    }
}