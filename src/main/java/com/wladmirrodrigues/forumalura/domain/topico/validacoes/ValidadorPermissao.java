package com.wladmirrodrigues.forumalura.domain.topico.validacoes;

import com.wladmirrodrigues.forumalura.domain.topico.Topico;

public interface ValidadorPermissao {
    public void validar(String token, Topico topico);
}
