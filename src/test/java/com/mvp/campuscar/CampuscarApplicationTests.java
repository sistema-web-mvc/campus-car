package com.mvp.campuscar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.mvp.campuscar.model.Usuario;
import com.mvp.campuscar.repository.CaronaRepository;
import com.mvp.campuscar.repository.SolicitacaoRepository;
import com.mvp.campuscar.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CampuscarApplicationTests {

	@Autowired
	private CaronaRepository caronaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void caronasIncluemONomeDoMotorista() {
		var caronas = caronaRepository.findAll();

		assertFalse(caronas.isEmpty());
		assertTrue(caronas.stream().allMatch(carona -> {
			assertNotNull(carona.getMotorista());
			return carona.getMotorista().getNome() != null
					&& !carona.getMotorista().getNome().isBlank();
		}));
	}

	@Test
	void paginasDeCadastroSaoExibidas() throws Exception {
		mockMvc.perform(get("/caronas/criar"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Criar carona")));

		mockMvc.perform(get("/usuarios/criar"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Criar usuário")));

		mockMvc.perform(get("/solicitacoes/criar"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Solicitar carona")));
	}

	@Test
	void criaUsuarioECarona() throws Exception {
		String token = UUID.randomUUID().toString();
		String emailPassageiro = "passageiro-" + token + "@example.com";
		mockMvc.perform(post("/usuarios/criar")
						.param("nome", "Passageiro de teste")
						.param("email", emailPassageiro)
						.param("telefone", "34999999999"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/usuarios"));

		String emailMotorista = "motorista-" + token + "@example.com";
		usuarioRepository.save(new Usuario(null, "Motorista de teste", emailMotorista, "34999999998", true));
		Long motoristaId = usuarioRepository.findAll().stream()
				.filter(usuario -> emailMotorista.equals(usuario.getEmail()))
				.findFirst()
				.orElseThrow()
				.getId();
		String dataHora = LocalDateTime.now().plusDays(1).withSecond(0).withNano(0)
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

		mockMvc.perform(post("/caronas/criar")
						.param("motoristaId", motoristaId.toString())
						.param("origem", "Centro")
						.param("destino", "Campus")
						.param("dataHora", dataHora)
						.param("vagasDisponiveis", "2"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/caronas"));

		mockMvc.perform(get("/caronas"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Motorista de teste")));
	}

	@Test
	void criaSolicitacaoParaCaronaCadastrada() throws Exception {
		String emailPassageiro = "solicitante-" + UUID.randomUUID() + "@example.com";
		usuarioRepository.save(new Usuario(null, "Passageiro solicitante", emailPassageiro, "34999999997", false));
		Long passageiroId = usuarioRepository.findAll().stream()
				.filter(usuario -> emailPassageiro.equals(usuario.getEmail()))
				.findFirst()
				.orElseThrow()
				.getId();
		Long caronaId = caronaRepository.findAll().get(0).getId();

		mockMvc.perform(post("/solicitacoes/criar")
						.param("caronaId", caronaId.toString())
						.param("passageiroId", passageiroId.toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/solicitacoes/criar?enviado=true"));

		assertTrue(solicitacaoRepository.findAll().stream()
				.anyMatch(solicitacao -> caronaId.equals(solicitacao.getCaronaId())
						&& passageiroId.equals(solicitacao.getPassageiroId())
						&& "PENDENTE".equals(solicitacao.getStatus())));

		mockMvc.perform(get("/solicitacoes"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Passageiro solicitante")));
	}

}
