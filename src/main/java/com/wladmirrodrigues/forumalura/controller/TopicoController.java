package com.wladmirrodrigues.forumalura.controller;
import com.wladmirrodrigues.forumalura.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @Autowired
    private TopicoRepository topicoRepository;
    @PostMapping
    @Transactional
    public ResponseEntity criarTopico(@RequestBody @Valid DadosCadastroTopico dados, @RequestHeader(name="Authorization") String headerToken, UriComponentsBuilder uriBuilder){
        var topico = topicoService.cadastrarTopico(dados, headerToken);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body( new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity obterTopicos(@PageableDefault(size = 10, sort = {"curso"}, direction = Sort.Direction.ASC) Pageable paginacao){
        var topicosPage = topicoRepository.findAll(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(topicosPage);
    }

}
