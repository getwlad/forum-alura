package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.curso.DadosCadastroCurso;
import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
 class TopicoRepositoryTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private TopicoRepository topicoRepository;

    @Test
    @DisplayName("Deve obter um tópico que já existe com o mesmo titulo e mensagem")
    void obterTopicoCenario1(){
        var dados = new DadosCadastroTopico("teste", "topico teste", "backend");
        cadastrarTopico(dados);
        var topicoExiste = topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
        assertThat(topicoExiste).isNotNull();
    }

    @Test
    @DisplayName("Não deve obter um tópico com titulo e mensagem diferentes")
    void obterTopicoCenario2(){
        var dados = new DadosCadastroTopico("teste", "topico teste", "backend");
        cadastrarTopico(dados);
        var topicoExiste = topicoRepository.findByTituloAndMensagem(dados.titulo(), "mensagem diferente");
        assertThat(topicoExiste).isNull();
    }



    private void cadastrarTopico(DadosCadastroTopico dados) {
        var curso = new Curso(new DadosCadastroCurso("backend"));
        var usuario = new Usuario("test",  "1234", "joao");
        em.persist(usuario);
        em.persist(curso);
        var topico = new Topico(dados ,usuario, curso);
        em.persist(topico);
    }
}
