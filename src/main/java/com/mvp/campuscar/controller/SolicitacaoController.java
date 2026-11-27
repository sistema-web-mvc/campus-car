package com.mvp.campuscar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvp.campuscar.model.Carona;
import com.mvp.campuscar.model.Solicitacao;
import com.mvp.campuscar.model.Usuario;
import com.mvp.campuscar.repository.CaronaRepository;
import com.mvp.campuscar.repository.SolicitacaoRepository;
import com.mvp.campuscar.repository.UsuarioRepository;
import com.mvp.campuscar.service.SolicitacaoService;

import jakarta.validation.Valid;

@Controller
public class SolicitacaoController {

    private final SolicitacaoRepository solicitacaoRepository;
    private final CaronaRepository caronaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(
            SolicitacaoRepository solicitacaoRepository,
            CaronaRepository caronaRepository,
            UsuarioRepository usuarioRepository,
            SolicitacaoService solicitacaoService) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.caronaRepository = caronaRepository;
        this.usuarioRepository = usuarioRepository;
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping("/solicitacoes")
    public String listar(Model model) {
        model.addAttribute("solicitacoes", solicitacaoRepository.findAll());
        return "solicitacoes";
    }

    @GetMapping("/solicitacoes/criar")
    public String formulario(
            @RequestParam(defaultValue = "false") boolean enviado,
            Model model) {
        model.addAttribute("solicitacao", new Solicitacao());
        model.addAttribute("caronas", caronaRepository.findAll());
        model.addAttribute("passageiros", passageiros());
        model.addAttribute("enviado", enviado);
        return "solicitacao-form";
    }

    @PostMapping("/solicitacoes/criar")
    public String criar(
            @Valid @ModelAttribute("solicitacao") Solicitacao solicitacao,
            BindingResult result,
            Model model) {
        List<Carona> caronas = caronaRepository.findAll();
        List<Usuario> passageiros = passageiros();

        if (result.hasErrors()) {
            model.addAttribute("caronas", caronas);
            model.addAttribute("passageiros", passageiros);
            model.addAttribute("enviado", false);
            return "solicitacao-form";
        }

        Map<String, String> erros = solicitacaoService.criarSolicitacao(solicitacao);
        if (!erros.isEmpty()) {
            erros.forEach((campo, mensagem) ->
                    result.rejectValue(campo, "solicitacao.invalida", mensagem));
            model.addAttribute("caronas", caronas);
            model.addAttribute("passageiros", passageiros);
            model.addAttribute("enviado", false);
            return "solicitacao-form";
        }

        return "redirect:/solicitacoes/criar?enviado=true";
    }

    private List<Usuario> passageiros() {
        return usuarioRepository.findAll();
    }
}