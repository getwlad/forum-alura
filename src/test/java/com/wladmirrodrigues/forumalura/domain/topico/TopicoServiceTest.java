package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.curso.DadosCadastroCurso;
import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
 class TopicoServiceTest {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private TokenService tokenService;

   @Autowired
    private TopicoService topicoService;

    @Test
    @DisplayName("Deve cadastrar um topico")
    @Transactional
    void testarCadastroTopico(){
        criarCurso();
        Usuario usuario = criarUsuario();
        var tokenUsuario = tokenService.gerarToken(usuario);
        var dadosCadastroTopico = new DadosCadastroTopico("duvida sobre", "esta correto isso?", "backend", "Sebastiao Ferreira");
        var topico = topicoService.cadastrarTopico(dadosCadastroTopico, tokenUsuario);
        assertThat(topico).isNotNull();
    }



    private Usuario criarUsuario() {
        var usuario = new Usuario("test",  "1234");
        usuarioRepository.save(usuario);
        return usuario;
    }
    private Curso criarCurso() {
        var curso = new Curso(new DadosCadastroCurso("backend"));
        cursoRepository.save(curso);
        return curso;
    }
}
