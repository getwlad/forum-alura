package com.wladmirrodrigues.forumalura.domain.resposta.validacoes;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.resposta.Resposta;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMensagemPertenceAoUsuario implements ValidadorPermissao {
    @Autowired
    private TokenService tokenService;
    @Override
    public void validar(String token, Resposta resposta) {
        var login = tokenService.getSubject(token);
        if( !(resposta.getUsuario().getLogin().equals(login))){
            throw  new ValidacaoException("O tópico não pertence ao usuário logado");
        }
    }
}
