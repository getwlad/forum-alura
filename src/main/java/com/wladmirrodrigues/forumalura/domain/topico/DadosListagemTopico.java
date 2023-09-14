package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.curso.Curso;


import java.time.LocalDateTime;

public record DadosListagemTopico(
        String titulo,
        String mensagem,
        String autor,
        LocalDateTime dataCriacao,
        Status status,
        String curso
) {
    public DadosListagemTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getDataCriacao(), topico.getStatus(), topico.getCurso().getNome());
    }
}