package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.resposta.DadosListagemResposta;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoTopico (
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        String curso,
        Long cursoId,
        String autor,
        Long usuarioId,
        List<DadosListagemResposta> respostas
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
                topico.getUsuario().getId(),
                topico.getListagemRespostas()
        );
    }
}
