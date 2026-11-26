package com.mvp.campuscar.controller;

import java.util.List;

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

import jakarta.validation.Valid;

@Controller
public class SolicitacaoController {

    private final SolicitacaoRepository solicitacaoRepository;
    private final CaronaRepository caronaRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoController(
            SolicitacaoRepository solicitacaoRepository,
            CaronaRepository caronaRepository,
            UsuarioRepository usuarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.caronaRepository = caronaRepository;
        this.usuarioRepository = usuarioRepository;
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

        if (solicitacaoRepository.existsByCaronaIdAndPassageiroId(solicitacao.getCaronaId(), solicitacao.getPassageiroId())) {
            // Se for uma aplicação web com templates (Thymeleaf, etc.):
            result.rejectValue("caronaId", "duplicada", "Você já solicitou esta carona.");
            return "solicitacao-form";
            
            // Ou, se for uma API REST:
            // return ResponseEntity.status(HttpStatus.CONFLICT).body("Solicitação já existente.");
        }

        if (solicitacao.getCaronaId() != null
                && caronas.stream().noneMatch(carona -> carona.getId().equals(solicitacao.getCaronaId()))) {
            result.rejectValue("caronaId", "solicitacao.caronaId.invalido", "Selecione uma carona cadastrada.");
        }
        if (solicitacao.getPassageiroId() != null
                && passageiros.stream().noneMatch(passageiro -> passageiro.getId().equals(solicitacao.getPassageiroId()))) {
            result.rejectValue("passageiroId", "solicitacao.passageiroId.invalido",
                    "Selecione um passageiro cadastrado.");
        }

        model.addAttribute("caronas", caronas);
        model.addAttribute("passageiros", passageiros);
        model.addAttribute("enviado", false);
        if (result.hasErrors()) {
            return "solicitacao-form";
        }

        solicitacao.setStatus("PENDENTE");
        solicitacaoRepository.save(solicitacao);
        return "redirect:/solicitacoes/criar?enviado=true";

    }

    private List<Usuario> passageiros() {
        return usuarioRepository.findAll();
    }
}
