package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.resposta.*;
import com.wladmirrodrigues.forumalura.domain.topico.TopicoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/topicos/{topicoId}/respostas")
@SecurityRequirement(name = "bearer-key")
public class RespostaController {
    @Autowired
    private RespostaService respostaService;
    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public ResponseEntity obterRespostas(@PathVariable Long topicoId, Pageable paginacao){
        if(!topicoRepository.existsById(topicoId)){
            throw new ValidacaoException("Tópico não encontrado");
        }
        var respostas = respostaRepository.findAllByTopicoId(topicoId, paginacao).map(DadosListagemResposta::new);;
        return ResponseEntity.ok(respostas);
    }
    @PostMapping
    public ResponseEntity enviarResposta(@RequestBody @Valid DadosRegistrarResposta dados, @PathVariable Long topicoId, @RequestHeader(name="Authorization") String headerToken, UriComponentsBuilder uriBuilder){
        var resposta = respostaService.cadastroResposta(topicoId, dados, headerToken);
        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body( new DadosListagemResposta(resposta));
    }
    @PutMapping("/{id}")
    public ResponseEntity atualizarResposta(@RequestBody @Valid DadosAtualizarResposta dados, @PathVariable Long id, @RequestHeader(name="Authorization") String headerToken, UriComponentsBuilder uriBuilder){
        if(!respostaRepository.existsById(id)){
            throw new ValidacaoException("Mensagem não encontrada");
        }

        var resposta = respostaRepository.getReferenceById(id);

        respostaService.verificarPermissoes(headerToken,  id);
        resposta.atualizar(dados);

        return ResponseEntity.ok( new DadosListagemResposta(resposta));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity excluirResposta(@PathVariable Long id, @RequestHeader(name="Authorization") String headerToken){
        if(!respostaRepository.existsById(id)){
            throw new ValidacaoException("Mensagem não encontrada");
        }
        var resposta = respostaRepository.getReferenceById(id);
        respostaService.verificarPermissoes(headerToken,  id);
        respostaRepository.delete(resposta);
        return ResponseEntity.noContent().build();

    }

}
