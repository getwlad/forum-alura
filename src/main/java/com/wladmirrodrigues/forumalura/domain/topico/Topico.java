package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.DadosCadastroCurso;
import com.wladmirrodrigues.forumalura.domain.resposta.DadosListagemResposta;
import com.wladmirrodrigues.forumalura.domain.resposta.Resposta;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
    private List<Resposta> resposta;

    public Topico(DadosCadastroTopico dados, Usuario usuario, Curso curso) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now().withNano(0).withSecond(0);
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
        if (dados.status() != null) {
            this.status = dados.status();
        }
    }

    public void atualizarCurso(Curso curso) {
        if(curso != null){
            this.curso = curso;
        }
    }

    public List<DadosListagemResposta> getListagemRespostas() {
        List<DadosListagemResposta> listaRespostas = new ArrayList<>();
        if(this.getResposta() == null){
            return listaRespostas;
        }

       this.getResposta().forEach(resposta -> {
           listaRespostas.add(new DadosListagemResposta(resposta.getMensagem(), resposta.getDataMensagem(), resposta.getUsuario().getNome()));
       });
        
        return listaRespostas;
    }
}
