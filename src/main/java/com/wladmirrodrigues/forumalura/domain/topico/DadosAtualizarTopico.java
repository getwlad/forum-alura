package com.wladmirrodrigues.forumalura.domain.topico;



public record DadosAtualizarTopico(
        String titulo,
        String mensagem,
        String curso,
        Status status

) {
}
