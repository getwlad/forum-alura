package com.wladmirrodrigues.forumalura.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query(
            """
        select p
        from Topico p
        where p.titulo = :titulo
        AND mensagem = :mensagem
        order by p.titulo
        LIMIT 1
"""
    )
    Topico findByTituloAndMensagem(String titulo, String mensagem);
}
