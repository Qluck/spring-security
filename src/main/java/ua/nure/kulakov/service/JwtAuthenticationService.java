package ua.nure.kulakov.service;

public interface JwtAuthenticationService {
    void authenticateByJwtToken(String token);
}
