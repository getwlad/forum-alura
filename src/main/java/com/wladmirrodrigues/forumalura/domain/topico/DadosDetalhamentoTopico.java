package com.wladmirrodrigues.forumalura.domain.topico;


import java.time.LocalDateTime;

public record DadosDetalhamentoTopico (
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        String curso,
        Long cursoId,
        String autor,
        Long usuarioId
){
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getCurso().getNome(),
                topico.getCurso().getId(),
                topico.getUsuario().getNome(),
                topico.getUsuario().getId()
        );
    }
}
