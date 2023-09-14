package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.curso.Curso;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico (
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        String curso,
        String autor
){
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getCurso().getNome(),
                topico.getAutor()
        );
    }
}
