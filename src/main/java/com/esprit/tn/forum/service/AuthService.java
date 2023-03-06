package com.esprit.tn.forum.service;

import com.esprit.tn.forum.dto.AuthenticationResponse;
import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.UniversityRepository;
import com.esprit.tn.forum.security.JwtProvider;
import com.esprit.tn.forum.dto.LoginRequest;
import com.esprit.tn.forum.dto.RefreshTokenRequest;
import com.esprit.tn.forum.dto.RegisterRequest;
import com.esprit.tn.forum.exceptions.ForumException;
import com.esprit.tn.forum.repository.UserRepository;
import com.esprit.tn.forum.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    private final UniversityRepository universityRepository;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
       user.setTypeUser(registerRequest.getTypeUser());
        user.setEnabled(false);
        University university = universityRepository.findById(registerRequest.getUniversityId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid university ID"));
        user.setUniversity(university);
        university.setRecruiter(user);
        userRepository.save(user);
        universityRepository.save(university);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ForumException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new ForumException("Invalid Token")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public Map<String, Integer> getUserStats() {
        int adminCount = userRepository.countByRole(TypeUser.Admin);
        int recuiterCount = userRepository.countByRole(TypeUser.Candidate);
        int condidateCount = userRepository.countByRole(TypeUser.Recruiter);

        Map<String, Integer> stats = new HashMap<>();

        stats.put("adminCount", adminCount);
        stats.put("recuiterCount", recuiterCount);
        stats.put("condidateCount", condidateCount);

        return stats;
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public int countUsersByTypeAndMonth(TypeUser typeUser, int year, int month) {
        Instant startDate = LocalDateTime.of(year, month, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);
        Instant endDate = LocalDateTime.of(year, month, Month.of(month).length(Year.isLeap(year)), 23, 59, 59).toInstant(ZoneOffset.UTC);
        return userRepository.countByTypeUserAndCreatedDateBetween(typeUser, startDate, endDate);
    }
}
