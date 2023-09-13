package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.domain.usuario.DadosUsuario;
import com.wladmirrodrigues.forumalura.domain.usuario.Usuario;
import com.wladmirrodrigues.forumalura.domain.usuario.UsuarioRepository;
import com.wladmirrodrigues.forumalura.infra.security.DadosTokenJWT;
import com.wladmirrodrigues.forumalura.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosUsuario dados){
        if(usuarioRepository.existsByLogin(dados.login())){
            throw new RuntimeException("Usuário já cadastrado");
        };
        var usuario = new Usuario(dados, passwordEncoder);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Cadastro realizado com sucesso");
    }
    @PostMapping("/login")
    public ResponseEntity fazerLogin(@RequestBody @Valid DadosUsuario dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
