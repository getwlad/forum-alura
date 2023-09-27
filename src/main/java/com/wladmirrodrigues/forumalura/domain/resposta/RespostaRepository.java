package com.wladmirrodrigues.forumalura.domain.resposta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    @Query("SELECT r FROM Resposta r WHERE r.topico.id = :topicoId")
    Page<Resposta> findAllByTopicoId(Long topicoId, Pageable paginacao);
}
