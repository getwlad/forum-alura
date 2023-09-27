package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
import com.wladmirrodrigues.forumalura.domain.curso.DadosCadastroCurso;
import com.wladmirrodrigues.forumalura.domain.resposta.*;
import com.wladmirrodrigues.forumalura.domain.topico.*;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class RespostaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private TopicoRepository topicoRepository;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private CursoRepository cursoRepository;
    @Autowired
    private JacksonTester<DadosRegistrarResposta> dadosRegistrarRespostaJson;
    @Autowired
    private JacksonTester<DadosAtualizarResposta> dadosAtualizarRespostaJson;
    @Autowired
    private JacksonTester<DadosListagemResposta> dadosListagemRespostaJson;
    @MockBean
    private RespostaRepository respostaRepository;

    @Test
    @DisplayName("Deve registrar uma resposta a um tópico válido")
    void registrarCenario1() throws Exception {
        var dadosReposta = new DadosRegistrarResposta("testando");
        var resposta = criarResposta();
        when(topicoRepository.existsById(any())).thenReturn(true);
        when(respostaRepository.save(any())).thenReturn(resposta);
       var response = mockMvc.perform(post("/topicos/1/respostas").contentType(MediaType.APPLICATION_JSON)
               .content(dadosRegistrarRespostaJson.write(dadosReposta).getJson())
               .header("Authorization", "token")
       ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(dadosListagemRespostaJson.write(new DadosListagemResposta(resposta)).getJson());
    }
    @Test
    @DisplayName("Deve atualizar uma resposta")
    @WithMockUser
    void atualizarCenario1() throws Exception {
        var dadosAtualizarResposta = new DadosAtualizarResposta("mensagem atualizada");
        var resposta = criarResposta();
        resposta.setMensagem("mensagem atualizada");
        var dadosAtualizados = new DadosListagemResposta(resposta);
        when(respostaRepository.save(any())).thenReturn(resposta);
        var response = mockMvc.perform(put("/topicos/1/respostas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAtualizarRespostaJson.write(dadosAtualizarResposta).getJson())
                .header("Authorization", "token")
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(dadosListagemRespostaJson.write(dadosAtualizados).getJson());
    }

    @Test
    @DisplayName("Deve excluir uma resposota")
    @WithMockUser
    void apagarCenario1() throws Exception{
        criarResposta();
        var response = mockMvc.perform(delete("/topicos/1/respostas/1")
                .header("Authorization", "token")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

    }
    @Test
    @DisplayName("Deve obter  as respostas de um tópico")
    @WithMockUser
    void oobterCenario1() throws Exception{
        var resposta = criarResposta();
        Page<Resposta> paginaVazia = mock(Page.class);
        when(paginaVazia.getTotalPages()).thenReturn(0);
        when(paginaVazia.getTotalElements()).thenReturn(0L);
        when(respostaRepository.findAllByTopicoId(any(Long.class), any(Pageable.class))).thenReturn(paginaVazia);
        var response = mockMvc.perform(get("/topicos/1/respostas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());



    }
    private Curso criarCurso() {
        var curso = new Curso(new DadosCadastroCurso("backend"));
        when(cursoRepository.findByNome(any())).thenReturn(curso);
        when(cursoRepository.existsByNome(any())).thenReturn(true);
        return curso;
    }


    private Usuario criarUsuario() {
        var usuario = new Usuario("test", "1234", "felipe");
        when(usuarioRepository.getReferenceByLogin(any())).thenReturn(usuario);
        when(usuarioRepository.findByLogin(any())).thenReturn(usuario);
        when(tokenService.getSubject(any())).thenReturn(usuario.getLogin());
        return usuario;
    }

    private Topico criarTopico(Usuario usuario, Curso curso) {
        var dados = new DadosCadastroTopico("teste", "deTeste", "backend");
        var topico = new Topico(dados, usuario, curso);
        when(topicoRepository.getReferenceById(any())).thenReturn(topico);
        when(topicoRepository.existsById(any())).thenReturn(true);
        return topico;
    }

    private Resposta criarResposta(){
        var curso= criarCurso();
        var usuario = criarUsuario();
        var topico = criarTopico(usuario, curso);
        var dadosReposta = new DadosRegistrarResposta("testando");
        var resposta = new Resposta(dadosReposta.mensagem(), topico, usuario);
        when(respostaRepository.getReferenceById(any())).thenReturn(resposta);
        when(respostaRepository.existsById(any())).thenReturn(true);
        return resposta;
    }
}