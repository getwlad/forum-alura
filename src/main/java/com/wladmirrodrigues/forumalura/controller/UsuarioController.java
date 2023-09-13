package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.domain.usuario.DadosCadastroUsuario;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados){
        if(usuarioRepository.existsByLogin(dados.login())){
            throw new RuntimeException("Usuário já cadastrado");
        };
        var usuario = new Usuario(dados, passwordEncoder);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Cadastro realizado com sucesso");
    }
}
