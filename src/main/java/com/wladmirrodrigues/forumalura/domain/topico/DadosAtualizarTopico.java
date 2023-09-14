package com.wladmirrodrigues.forumalura.domain.topico;


import com.wladmirrodrigues.forumalura.domain.curso.DadosCadastroCurso;

public record DadosAtualizarTopico(
        String titulo,
        String mensagem,
        String curso,
        String autor,
        Status status

) {
}
