package com.wladmirrodrigues.forumalura.domain.resposta.validacoes;

import com.wladmirrodrigues.forumalura.domain.resposta.Resposta;

public interface ValidadorPermissao {
    public void validar(String token, Resposta resposta);
}
