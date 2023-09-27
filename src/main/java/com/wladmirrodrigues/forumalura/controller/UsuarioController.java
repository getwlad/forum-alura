package com.wladmirrodrigues.forumalura.controller;

import com.wladmirrodrigues.forumalura.domain.ValidacaoException;
import com.wladmirrodrigues.forumalura.domain.usuario.DadosCadastroUsuario;
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
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder){
        if(usuarioRepository.existsByLogin(dados.login())){
            throw new ValidacaoException("Usuário já cadastrado");
        };
        var senhaEncriptada = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados.login(), senhaEncriptada, dados.nome());
        usuarioRepository.save(usuario);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body("Cadastro realizado como sucesso");

    }
    @PostMapping("/login")
    public ResponseEntity fazerLogin(@RequestBody @Valid DadosUsuario dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
