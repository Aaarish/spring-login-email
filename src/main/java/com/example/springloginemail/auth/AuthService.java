package com.example.springloginemail.auth;

import com.example.springloginemail.email.EmailSender;
import com.example.springloginemail.jwt.JwtUtil;
import com.example.springloginemail.user.AppUser;
import com.example.springloginemail.user.AppUserRepo;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserRepo userRepo;
    @Autowired
    private EmailSender emailSender;

    public AuthResponse authenticate(AuthRequest authRequest) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());

        Authentication authenticatedRequest = authenticationManager.authenticate(authToken);
        String token = null;

        if(authenticatedRequest != null){
            UserDetails userDetails = userRepo.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("No user exists with this email"));
            token = jwtUtil.generateToken(userDetails);
        }

        try {
            emailSender.sendEMail("aarishnew@gmail.com", "aarishm767@gmail.com", "Jwt for access", token);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return AuthResponse.builder()
                .jwt(token)
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        AppUser user = new AppUser(registerRequest.getUserId(), registerRequest.getName(), registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));

        AppUser registeredUSer = userRepo.save(user);

        String token = jwtUtil.generateToken(registeredUSer);

        return AuthResponse.builder()
                .jwt(token)
                .build();
    }
}
