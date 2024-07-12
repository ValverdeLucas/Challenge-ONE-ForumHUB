package one.challenge.valverde.lucas.forum_hub.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import one.challenge.valverde.lucas.forum_hub.domain.usuario.dto.AutenticacaoDTO;
import one.challenge.valverde.lucas.forum_hub.domain.usuario.Usuario;
import one.challenge.valverde.lucas.forum_hub.infra.security.TokenJtwDTO;
import one.challenge.valverde.lucas.forum_hub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("login")
@Tag(name = "Auth Controller")
public class AutorizacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var auth = manager.authenticate(authToken);

        var tokenJTW = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new TokenJtwDTO(tokenJTW));

    }

}
