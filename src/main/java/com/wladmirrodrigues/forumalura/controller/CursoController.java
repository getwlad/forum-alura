package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.curso.DadosCadastroCurso;
import com.wladmirrodrigues.forumalura.domain.curso.Curso;
import com.wladmirrodrigues.forumalura.domain.curso.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;
    @Transactional
    @PostMapping
    public ResponseEntity cadastrarCurso(@RequestBody @Valid DadosCadastroCurso dados){
      var curso = new Curso(dados);
      cursoRepository.save(curso);
      return ResponseEntity.ok("Curso cadastrado");
    }
}
