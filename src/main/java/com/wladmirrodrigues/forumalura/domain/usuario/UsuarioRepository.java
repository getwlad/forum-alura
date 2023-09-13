package com.wladmirrodrigues.forumalura.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByLogin(String login);

    UserDetails findByLogin(String login);

    Usuario getReferenceByLogin(String login);
}
