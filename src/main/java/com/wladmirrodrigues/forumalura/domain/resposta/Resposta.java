package com.wladmirrodrigues.forumalura.domain.resposta;

import com.wladmirrodrigues.forumalura.domain.topico.Topico;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Resposta")
@Table(name = "respostas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Resposta {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private LocalDateTime dataMensagem = LocalDateTime.now().withNano(0).withSecond(0);;;

    @ManyToOne
    @JoinColumn(name = "topicos_id")
    private Topico topico;
    @ManyToOne
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    public Resposta(String mensagem, Topico topico, Usuario usuario) {
        this.mensagem = mensagem;
        this.dataMensagem = LocalDateTime.now().withNano(0).withSecond(0);
        this.topico = topico;
        this.usuario = usuario;
    }

    public void atualizar(DadosAtualizarResposta dados) {
        this.mensagem = dados.mensagem();
    }
}
