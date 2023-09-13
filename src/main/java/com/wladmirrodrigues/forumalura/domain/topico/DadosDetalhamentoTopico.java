package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.curso.Curso;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico (
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        Curso curso,
        String autor
){
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getCurso(),
                topico.getAutor()
        );
    }
}
