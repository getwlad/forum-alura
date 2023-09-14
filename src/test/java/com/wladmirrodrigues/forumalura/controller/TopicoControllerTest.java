package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.curso.DadosCadastroCurso;
import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
import com.wladmirrodrigues.forumalura.domain.topico.DadosCadastroTopico;
import com.wladmirrodrigues.forumalura.domain.topico.DadosDetalhamentoTopico;
import com.wladmirrodrigues.forumalura.domain.topico.Topico;
import com.wladmirrodrigues.forumalura.domain.topico.TopicoRepository;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


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
        var dadosCadastroTopico = new DadosCadastroTopico("duvida sobre", "esta correto isso?", "backend", "Sebastiao Ferreira");
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
                                new DadosCadastroTopico(null, "213", "backend", "123")
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
                                new DadosCadastroTopico("teste", "213", "backend", "123")
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

    private Curso criarCurso() {
       var curso = new Curso(new DadosCadastroCurso("backend"));
        when(cursoRepository.findByNome(any())).thenReturn(curso);
        when(cursoRepository.existsByNome(any())).thenReturn(true);
       return curso;
    }

    private Usuario criarUsuario() {
        var usuario = new Usuario("test",  "1234");
        when(usuarioRepository.getReferenceByLogin(any())).thenReturn(usuario);
        when(usuarioRepository.findByLogin(any())).thenReturn(usuario);
         return usuario;
    }

}
