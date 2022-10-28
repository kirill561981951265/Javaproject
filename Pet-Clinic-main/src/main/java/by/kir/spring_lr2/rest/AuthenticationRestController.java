package by.kir.spring_lr2.rest;

import by.kir.spring_lr2.aop.LogAnnotation;
import by.kir.spring_lr2.dto.UserAuthDto;
import by.kir.spring_lr2.exceptions.AuthenticationException;
import by.kir.spring_lr2.repository.UserRep;
import by.kir.spring_lr2.dto.TokenResponseDto;
import by.kir.spring_lr2.model.User;
import by.kir.spring_lr2.security.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name="Authentication REST Controller", description="The controller accepts requests from the login page")
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRep userRep;

    private final TokenProvider tokenProvider;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, UserRep userRep, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRep = userRep;
        this.tokenProvider = tokenProvider;
    }

    @Operation(
            summary = "User authentication",
            description = "Allows you to authentication"
    )
    @LogAnnotation
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserAuthDto userAuthDto) throws AuthenticationException {
        TokenResponseDto tokenResponseDto = new TokenResponseDto();
            final String username = userAuthDto.getUsername();
            final String password = userAuthDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenProvider.createToken(username);
            User user = userRep.findByUsername(username);
            tokenResponseDto.setUsername(username);
            tokenResponseDto.setRole(user.getRoles().toString());
            tokenResponseDto.setToken(token);
            return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }
}
