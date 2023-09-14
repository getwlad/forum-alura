package com.wladmirrodrigues.forumalura.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCurso(
        @NotBlank
        String nome

) {
}
