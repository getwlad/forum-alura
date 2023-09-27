package com.wladmirrodrigues.forumalura.domain.resposta;

import java.time.LocalDateTime;

public record DadosListagemResposta(Long id, String mensagem, LocalDateTime dataMensagem, String autor) {

    public DadosListagemResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataMensagem(), resposta.getUsuario().getNome());
    }
}
