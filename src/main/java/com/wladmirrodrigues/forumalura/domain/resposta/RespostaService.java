package com.wladmirrodrigues.forumalura.domain.resposta;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.resposta.validacoes.ValidadorPermissao;
import com.wladmirrodrigues.forumalura.domain.topico.TopicoRepository;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private List<ValidadorPermissao> validadorPermissaoList;

    public Resposta cadastroResposta(Long topicoId, DadosRegistrarResposta dados, String headerToken) {
        if (!topicoRepository.existsById(topicoId)) {
            throw new ValidacaoException("Topico não encontrado");
        }
        var token = headerToken.replace("Bearer", "").trim();
        var login = tokenService.getSubject(token);
        var usuario = usuarioRepository.getReferenceByLogin(login);

        var topico = topicoRepository.getReferenceById(topicoId);

        var resposta = new Resposta(dados.mensagem(), topico, usuario);
        respostaRepository.save(resposta);
        return resposta;
    }

    public void verificarPermissoes(String headerToken, Long id){
        var token = headerToken.replace("Bearer", "").trim();
        var resposta = respostaRepository.getReferenceById(id);
        validadorPermissaoList.forEach(validador -> validador.validar(token, resposta));
    }
}
