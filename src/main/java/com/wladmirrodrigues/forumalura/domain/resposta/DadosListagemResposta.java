package com.wladmirrodrigues.forumalura.domain.resposta;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record DadosListagemResposta(String mensagem, LocalDateTime dataMensagem, String autor) {

    public DadosListagemResposta(Resposta resposta) {
        this(resposta.getMensagem(), resposta.getDataMensagem(), resposta.getUsuario().getNome());
    }
}
