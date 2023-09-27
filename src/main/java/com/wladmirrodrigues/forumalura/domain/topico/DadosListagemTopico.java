package com.wladmirrodrigues.forumalura.domain.topico;




import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long topicoId,
        String titulo,
        String mensagem,
        String autor,
        LocalDateTime dataCriacao,
        Status status,
        String curso
) {
    public DadosListagemTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getUsuario().getNome(), topico.getDataCriacao(), topico.getStatus(), topico.getCurso().getNome());
    }
}
