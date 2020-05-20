package ua.nure.kulakov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.kulakov.service.JwtAuthenticationService;

@RestController("/admin")
public class AuthenticationController {

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @PostMapping("/auth")
    public ResponseEntity<HttpStatus> authentication(String token) {
        jwtAuthenticationService.authenticateByJwtToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
