package com.ohmydog.resources;

import com.ohmydog.dto.LoginDTO;
import com.ohmydog.entities.User;
import com.ohmydog.infra.security.TokenService;
import com.ohmydog.infra.security.UserJwtDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthResource {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthResource(AuthenticationManager manager,
                        TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<UserJwtDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        Authentication authentication = manager.authenticate(authenticationToken);

        var tokenJWT = this.tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new UserJwtDTO(tokenJWT));
    }
}
