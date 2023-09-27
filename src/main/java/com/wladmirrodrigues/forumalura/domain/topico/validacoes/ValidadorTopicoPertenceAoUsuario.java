package com.wladmirrodrigues.forumalura.domain.topico.validacoes;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.topico.Topico;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoPertenceAoUsuario implements ValidadorPermissao {
    @Autowired
    private TokenService tokenService;
    @Override
    public void validar(String token, Topico topico) {
        var login = tokenService.getSubject(token);
        if( !(topico.getUsuario().getLogin().equals(login))){
            throw  new ValidacaoException("O tópico não pertence ao usuário logado");
        }
    }
}
