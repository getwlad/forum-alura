package com.wladmirrodrigues.forumalura.controller;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
import com.wladmirrodrigues.forumalura.domain.topico.*;
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
    private TopicoService topicoService;
    @PostMapping
    @Transactional
    public ResponseEntity criarTopico(@RequestBody @Valid DadosCadastroTopico dados, @RequestHeader(name="Authorization") String headerToken, UriComponentsBuilder uriBuilder){
        var topico = topicoService.cadastrarTopico(dados, headerToken);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body( new DadosDetalhamentoTopico(topico));
    }
}
