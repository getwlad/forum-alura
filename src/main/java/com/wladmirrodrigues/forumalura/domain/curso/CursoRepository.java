package com.wladmirrodrigues.forumalura.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String curso);

    boolean existsByNome(String curso);
}
