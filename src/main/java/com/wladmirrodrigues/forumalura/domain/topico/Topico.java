package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.DadosCadastroCurso;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of ="id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private String autor;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cursos_id")
    private Curso curso;

    public Topico(DadosCadastroTopico dados, Usuario usuario, Curso curso) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = dados.autor();
        this.dataCriacao = LocalDateTime.now().withNano(0).withSecond(0);;
        this.status = Status.ABERTO;
        this.usuario = usuario;
        this.curso = curso;
    }


    public void atualizar(DadosAtualizarTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (dados.autor() != null) {
            this.autor = dados.autor();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
    }

    public void atualizarCurso(Curso curso) {
        if(curso != null){
            this.curso = curso;
        }
    }
}
