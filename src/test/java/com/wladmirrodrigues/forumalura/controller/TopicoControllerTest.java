package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.domain.curso.DadosCadastroCurso;
import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
 class TopicoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @Autowired
    private JacksonTester<DadosCadastroTopico> dadosCadastroTopicoJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoTopico> dadosDetalhamentoTopicoJson;
    @Autowired
    private JacksonTester<DadosAtualizarTopico> dadosAtualizarTopicoJson;
    @MockBean
    private TopicoRepository topicoRepository;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private CursoRepository cursoRepository;

    @Test
    @DisplayName("Deve cadastrar um tópico com informações válidas")
    @WithMockUser
    @Transactional
    void cadastroCenario1() throws Exception {
        Usuario usuario = criarUsuario();
        var curso = criarCurso();
        var dadosCadastroTopico = new DadosCadastroTopico("duvida sobre", "esta correto isso?", "backend");
        var topico = new Topico(dadosCadastroTopico, usuario, curso);

        when(topicoRepository.save(any())).thenReturn(topico);
        var response = mockMvc.perform(post("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "token")
                .content(dadosCadastroTopicoJson.write(
                                dadosCadastroTopico
                ).getJson()
                )
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var dadosDetalhamentoTopico = new DadosDetalhamentoTopico(topico);
        var jsonEsperado = dadosDetalhamentoTopicoJson.write(dadosDetalhamentoTopico).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve obter erro 400 ao cadastrar um topico com informações inválidas")
    @WithMockUser
    @Transactional
    void cadastroCenario2()  throws Exception {
        criarUsuario();
        var response = mockMvc.perform(post("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "token")
                .content(dadosCadastroTopicoJson.write(
                                new DadosCadastroTopico(null, "213", "backend")
                        ).getJson()
                )
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Deve obter erro 400 ao cadastrar um topico com um curso não cadastrado")
    @WithMockUser
    @Transactional
    void cadastroCenario3()  throws Exception {
       criarUsuario();
        var response = mockMvc.perform(post("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "token")
                .content(dadosCadastroTopicoJson.write(
                                new DadosCadastroTopico("teste", "213", "backend")
                        ).getJson()
                )
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Deve obter a lista de tópicos com paginação")
    @WithMockUser
    void listagemCenario1()  throws Exception {
        criarUsuario();
        criarCurso();
        Page<Topico> paginaVazia = mock(Page.class);
        when(paginaVazia.getTotalPages()).thenReturn(0);
        when(paginaVazia.getTotalElements()).thenReturn(0L);
        when(topicoRepository.findAll(any(Pageable.class))).thenReturn(paginaVazia);

        var response = mockMvc.perform(get("/topicos")
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }
    @Test
    @DisplayName("Deve obter um tópico")
    @WithMockUser
    void detalharCenario1()  throws Exception {
        var usuario = criarUsuario();
        var curso = criarCurso();
        criarTopico(usuario, curso);
        when(topicoRepository.existsById(any())).thenReturn(true);
        var response = mockMvc.perform(get("/topicos/1")
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("id");
    }
    @Test
    @DisplayName("Deve excluir um tópico")
    @WithMockUser
    void excluirCenario1()  throws Exception {
        var usuario = criarUsuario();
        var curso = criarCurso();
        var topico = criarTopico(usuario, curso);
        when(topicoRepository.existsById(any())).thenReturn(true);
        var response = mockMvc.perform(delete("/topicos/1").header("Authorization", "token")
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
    @Test
    @DisplayName("Deve atualizar um tópico")
    @WithMockUser
    void atualizarCenario1()  throws Exception {
        var usuario = criarUsuario();
        var curso = criarCurso();
        var topico = criarTopico(usuario, curso);
        var cursoAtualizado = new Curso(new DadosCadastroCurso("frontend"));
        when(cursoRepository.findByNome(any())).thenReturn(cursoAtualizado);
        when(topicoRepository.existsById(any())).thenReturn(true);

        var dados = new DadosAtualizarTopico("213", null, "frontend", null);
        var response = mockMvc.perform(put("/topicos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAtualizarTopicoJson.write(dados).getJson())
                .header("Authorization", "token")
        ).andReturn().getResponse();

        topico.atualizar(dados);
        topico.atualizarCurso(cursoAtualizado);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(dadosDetalhamentoTopicoJson.write(new DadosDetalhamentoTopico(topico)).getJson());

    }

    private Curso criarCurso() {
       var curso = new Curso(new DadosCadastroCurso("backend"));
        when(cursoRepository.findByNome(any())).thenReturn(curso);
        when(cursoRepository.existsByNome(any())).thenReturn(true);
       return curso;
    }

    private Usuario criarUsuario() {
        var usuario = new Usuario("test",  "1234", "felipe");
        when(usuarioRepository.getReferenceByLogin(any())).thenReturn(usuario);
        when(usuarioRepository.findByLogin(any())).thenReturn(usuario);
        //definindo para que o token service retorne o mesmo login do usuário
        when(tokenService.getSubject(any())).thenReturn("test");
         return usuario;
    }
    private Topico criarTopico(Usuario usuario, Curso curso){
        var dados = new DadosCadastroTopico("teste", "deTeste", "backend");
        var topico = new Topico(dados, usuario, curso);
        when(topicoRepository.getReferenceById(any())).thenReturn(topico);
        return topico;
    }

}
