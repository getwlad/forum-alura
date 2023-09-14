package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
import com.wladmirrodrigues.forumalura.domain.topico.validacoes.ValidadorCadastroTopico;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private List<ValidadorCadastroTopico> validadores;
    public Topico cadastrarTopico(DadosCadastroTopico dados, String headerToken) {
        validadores.forEach(v -> v.validar(dados));

        var token = headerToken.replace("Bearer", "").trim();
        var login = tokenService.getSubject(token);
        var usuario = usuarioRepository.getReferenceByLogin(login);
        if(!cursoRepository.existsByNome(dados.curso())){
            throw new ValidacaoException("Curso não cadastrado");
        }
        var curso = obterCurso(dados.curso());
        var topico = new Topico(dados, usuario, curso);

        topicoRepository.save(topico);
        return topico;
    }

    public Curso obterCurso(String nome) {
        return cursoRepository.findByNome(nome);
    }
}
