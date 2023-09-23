package com.wladmirrodrigues.forumalura.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario ( @NotBlank
                                     String login,
                                     @NotBlank
                                     String senha,
                                     @NotBlank
                                     String nome) {

}
