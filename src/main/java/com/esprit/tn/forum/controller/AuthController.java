package com.esprit.tn.forum.controller;

import com.esprit.tn.forum.dto.AuthenticationResponse;
import com.esprit.tn.forum.dto.LoginRequest;
import com.esprit.tn.forum.dto.RefreshTokenRequest;
import com.esprit.tn.forum.dto.RegisterRequest;
import com.esprit.tn.forum.model.TypeUser;
import com.esprit.tn.forum.repository.UserRepository;
import com.esprit.tn.forum.service.AuthService;
import com.esprit.tn.forum.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

        private final AuthService authService;

        private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }
    @GetMapping("/stats")
    public Map<String, Integer> getUserStats() {
        return authService.getUserStats();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
    @GetMapping("/users/count")
    public ResponseEntity<Map<String, Integer>> countUsersByTypeAndMonth(
            @RequestParam(value = "type") TypeUser typeUser,
            @RequestParam(value = "year") int year,
            @RequestParam(value = "month") int month) {

        Instant startDate = LocalDateTime.of(year, month, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);
        Instant endDate = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59, 59).toInstant(ZoneOffset.UTC);
        int count = userRepository.countByTypeUserAndCreatedDateBetween(typeUser, startDate, endDate);

        Map<String, Integer> result = new HashMap<>();
        result.put(typeUser.toString(), count);

        return ResponseEntity.ok(result);
    }
}
