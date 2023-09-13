package com.wladmirrodrigues.forumalura.controller;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
import com.wladmirrodrigues.forumalura.domain.topico.DadosCadastroTopico;
import com.wladmirrodrigues.forumalura.domain.topico.DadosDetalhamentoTopico;
import com.wladmirrodrigues.forumalura.domain.topico.Topico;
import com.wladmirrodrigues.forumalura.domain.topico.TopicoRepository;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity criarTopico(@RequestBody @Valid DadosCadastroTopico dados, @RequestHeader(name="Authorization") String headerToken, UriComponentsBuilder uriBuilder){
        var token = headerToken.replace("Bearer", "").trim();
        var login = tokenService.getSubject(token);
        var usuario = usuarioRepository.getReferenceByLogin(login);
        if(!cursoRepository.existsByNome(dados.curso())){
            throw new RuntimeException("Curso não cadastrado");
        }
        var curso = cursoRepository.findByNome(dados.curso());
        var topico = new Topico(dados, usuario, curso);

        topicoRepository.save(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body( new DadosDetalhamentoTopico(topico));
    }
}
