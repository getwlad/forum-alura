package com.wladmirrodrigues.forumalura.domain.resposta;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.topico.TopicoRepository;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespostaService {
    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    public Resposta cadastroResposta(DadosRegistrarResposta dados, String headerToken) {
        if (!topicoRepository.existsById(dados.topicoId())) {
            throw new ValidacaoException("Topico não encontrado");
        }
        var token = headerToken.replace("Bearer", "").trim();
        var login = tokenService.getSubject(token);
        var usuario = usuarioRepository.getReferenceByLogin(login);

        var topico = topicoRepository.getReferenceById(dados.topicoId());

        var resposta = new Resposta(dados.mensagem(), topico, usuario);
        respostaRepository.save(resposta);
        return resposta;
    }

    ;
}
