package com.wladmirrodrigues.forumalura.domain.topico.validacoes;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.topico.DadosCadastroTopico;
import com.wladmirrodrigues.forumalura.domain.topico.Topico;
import com.wladmirrodrigues.forumalura.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTituloMensagemJaExistem implements ValidadorCadastroTopico{
    @Autowired
    private TopicoRepository topicoRepository;

    public void validar(DadosCadastroTopico dados) {
        Topico topico = topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
        if(topico != null){
            throw new ValidacaoException("Uma dúvida igual já foi postada");
        }
    }
}
