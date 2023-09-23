package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.domain.resposta.DadosListagemResposta;
import com.wladmirrodrigues.forumalura.domain.resposta.DadosRegistrarResposta;
import com.wladmirrodrigues.forumalura.domain.resposta.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respostas")
public class RespostasController {
    @Autowired
    private RespostaService respostaService;
    @PostMapping
    public ResponseEntity enviarResposta(@RequestBody @Valid DadosRegistrarResposta dados, @RequestHeader(name="Authorization") String headerToken, UriComponentsBuilder uriBuilder){
        var resposta = respostaService.cadastroResposta(dados, headerToken);
        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body( new DadosListagemResposta(resposta));
    }
}
