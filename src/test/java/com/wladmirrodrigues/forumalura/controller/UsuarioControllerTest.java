package com.wladmirrodrigues.forumalura.controller;


import com.wladmirrodrigues.forumalura.domain.usuario.DadosCadastroUsuario;
import com.wladmirrodrigues.forumalura.domain.usuario.DadosUsuario;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
 class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JacksonTester<DadosUsuario> dadosUsuarioJson;
    @Autowired
    private JacksonTester<DadosCadastroUsuario> dadosCadastroUsuarioJson;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    @DisplayName("Deve cadastrar um usuário com informações válidas e senha encriptada")
    void testarCadastroUsuario()  throws Exception{
        String senha = "senha";
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuarioArgument = invocation.getArgument(0);
            // Verifica se a senha foi corretamente encriptada
            assertThat(passwordEncoder.matches(senha, usuarioArgument.getSenha())).isTrue();
            return usuarioArgument; // Simula a resposta do método save
        });
        var response = mockMvc.perform(post("/usuario/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroUsuarioJson.write(new DadosCadastroUsuario("login", senha, "felipe")).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    @DisplayName("Não deve cadastrar um usuário já cadastrado")
    void testarCadastroUsuario2()  throws Exception{
        when(usuarioRepository.existsByLogin(any())).thenReturn(true);
        var response = mockMvc.perform(post("/usuario/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroUsuarioJson.write(new DadosCadastroUsuario("login", "senha", "joao")).getJson())
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Deve fazer login com credenciais válidas e obter tokenJWT")
    void testarLogin()  throws Exception{
        String login = "login";
        String senha = "senha";
        String senhaEncriptada = passwordEncoder.encode(senha);
        Usuario usuario = new Usuario(login, senhaEncriptada, "felipe");
        when(usuarioRepository.findByLogin(login)).thenReturn(usuario);
       var response = mockMvc.perform(post("/usuario/login")
               .contentType(MediaType.APPLICATION_JSON)
               .content(dadosUsuarioJson.write(new DadosUsuario("login", "senha")).getJson()))
               .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("token");
    }
    @Test
    @DisplayName("Não deve fazer login com credenciais inválidas")
    void testarLogin2()  throws Exception{
        String login = "login";
        String senha = "senha";
        String senhaEncriptada = passwordEncoder.encode(senha);
        Usuario usuario = new Usuario(login, senhaEncriptada, "felipe");
        when(usuarioRepository.findByLogin(login)).thenReturn(usuario);
        var response = mockMvc.perform(post("/usuario/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosUsuarioJson.write(new DadosUsuario("login", "senhaErrada")).getJson()))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

    }

}
