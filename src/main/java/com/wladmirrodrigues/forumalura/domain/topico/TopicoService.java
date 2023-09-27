package com.wladmirrodrigues.forumalura.domain.topico;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
import com.wladmirrodrigues.forumalura.domain.topico.validacoes.ValidadorCadastroTopico;
import com.wladmirrodrigues.forumalura.domain.topico.validacoes.ValidadorPermissao;
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
    private List<ValidadorCadastroTopico> validadoresCadastro;
    @Autowired
    private List<ValidadorPermissao> validadoresPermissao;
    public Topico cadastrarTopico(DadosCadastroTopico dados, String headerToken) {
        validadoresCadastro.forEach(v -> v.validar(dados));

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

    public Topico atualizarTopico(Long id, DadosAtualizarTopico dados, String token) {
        if(!topicoRepository.existsById(id)){
            throw new ValidacaoException("Tópico não encontrado");
        }
        var topico = topicoRepository.getReferenceById(id);

        validarProprietarioTopico(token, topico);

        if(dados.curso() != null){
            var curso = this.obterCurso(dados.curso());
            if(curso == null){
                throw new ValidacaoException("O curso informado não existe, certifique se passar corretamente o nome");
            };
            topico.atualizarCurso(curso);
        }
        topico.atualizar(dados);
        return topico;
    }

    public void validarProprietarioTopico(String token, Topico topico){
        validadoresPermissao.forEach(v -> v.validar(token, topico));
    }
}
