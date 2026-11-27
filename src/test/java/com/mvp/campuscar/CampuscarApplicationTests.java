package com.mvp.campuscar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Compartilhe trajetos")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/caronas\"")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/usuarios\"")));

		mockMvc.perform(get("/caronas/criar"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Criar carona")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Aquila Silva")));

		mockMvc.perform(get("/usuarios/criar"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Criar usuário")));

		mockMvc.perform(get("/solicitacoes/criar"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Solicitar carona")));

		mockMvc.perform(get("/caronas"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"#caronas-disponiveis\"")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/solicitacoes\"")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/caronas/criar\"")));

		mockMvc.perform(get("/usuarios"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"#lista-usuarios\"")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/usuarios/criar\"")));
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

		Long motoristaId = usuarioRepository.findAll().stream()
				.filter(Usuario::isMotorista)
				.findFirst()
				.orElseThrow()
				.getId();
		String disponibilidade = "De segunda a quarta pela manhã";

		mockMvc.perform(post("/caronas/criar")
						.param("motoristaId", motoristaId.toString())
						.param("origem", "Centro")
						.param("destino", "Campus")
						.param("disponibilidade", disponibilidade)
						.param("vagasDisponiveis", "2"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/caronas"));

		mockMvc.perform(get("/caronas"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Aquila Silva")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString(disponibilidade)));
	}

	@Test
	void rejeitaCaronaComMotoristaIndisponivel() throws Exception {
		mockMvc.perform(post("/caronas/criar")
						.param("motoristaId", "-1")
						.param("origem", "Centro")
						.param("destino", "Campus")
						.param("disponibilidade", "Todos os dias")
						.param("vagasDisponiveis", "2"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString(
						"Selecione um motorista disponível.")));
	}

	@Test
	void criaSolicitacaoParaCaronaCadastrada() throws Exception {
		String emailMotorista = "solicitante-" + UUID.randomUUID() + "@example.com";
		usuarioRepository.save(new Usuario(null, "Motorista solicitante", emailMotorista, "34999999997", true));
		Long passageiroId = usuarioRepository.findAll().stream()
				.filter(usuario -> emailMotorista.equals(usuario.getEmail()))
				.findFirst()
				.orElseThrow()
				.getId();
		Long caronaId = caronaRepository.findAll().get(0).getId();

		mockMvc.perform(get("/solicitacoes/criar"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Motorista solicitante")));

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
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Motorista solicitante")));
	}

	@Test
	void rejeitaSolicitacaoDoMotoristaParaPropriaCarona() throws Exception {
		var carona = caronaRepository.findAll().get(0);

		mockMvc.perform(post("/solicitacoes/criar")
						.param("caronaId", carona.getId().toString())
						.param("passageiroId", carona.getMotoristaId().toString()))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString(
						"Você não pode solicitar sua própria carona.")));
	}

	@Test
	void rejeitaSolicitacaoDuplicadaParaMesmaCarona() throws Exception {
		String email = "solicitante-" + UUID.randomUUID() + "@example.com";
		usuarioRepository.save(new Usuario(null, "Solicitante", email, "34999999996", false));
		Long usuarioId = usuarioRepository.findAll().stream()
				.filter(usuario -> email.equals(usuario.getEmail()))
				.findFirst()
				.orElseThrow()
				.getId();
		Long caronaId = caronaRepository.findAll().get(0).getId();

		mockMvc.perform(post("/solicitacoes/criar")
						.param("caronaId", caronaId.toString())
						.param("passageiroId", usuarioId.toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/solicitacoes/criar?enviado=true"));

		mockMvc.perform(post("/solicitacoes/criar")
						.param("caronaId", caronaId.toString())
						.param("passageiroId", usuarioId.toString()))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString(
						"Este usuário já solicitou esta carona.")));
	}

	@Test
	void mostraErrosQuandoCamposObrigatoriosEstaoVazios() throws Exception {
		mockMvc.perform(post("/solicitacoes/criar"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Selecione uma carona.")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Selecione um usuário.")));
	}

}
