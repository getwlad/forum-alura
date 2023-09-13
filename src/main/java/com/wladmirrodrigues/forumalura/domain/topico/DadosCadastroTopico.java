package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record DadosCadastroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotBlank
        String curso,
        @NotBlank
        String autor
        ) {
}
