package com.wladmirrodrigues.forumalura.curso;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCurso(
        @NotBlank
        String nome

) {
}
