package com.wladmirrodrigues.forumalura.domain.resposta;

import jakarta.validation.constraints.NotBlank;

public record DadosRegistrarResposta(
        @NotBlank
        String mensagem
) {
}
