package com.wladmirrodrigues.forumalura.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosRegistrarResposta(
        @NotBlank
        String mensagem,
        @NotNull
        Long topicoId
) {
}
