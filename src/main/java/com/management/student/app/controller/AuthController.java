package com.management.student.app.controller;
import com.management.student.app.dto.AuthToken;
import com.management.student.app.dto.AuthenticationDto;
import com.management.student.app.dto.UserDto;
import com.management.student.app.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
@RequestMapping("auth/")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/sign-in")
    public ResponseEntity<AuthToken> signIn(@RequestBody AuthenticationDto authenticationDto) {
        return ResponseEntity.ok (authService.signIn (authenticationDto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserDto userDto) {
        authService.signUp (userDto);
        return ResponseEntity.ok ().build ();
    }
}
