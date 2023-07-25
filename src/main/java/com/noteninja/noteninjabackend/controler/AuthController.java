package com.noteninja.noteninjabackend.controler;

import com.noteninja.noteninjabackend.JwtUtils;
import com.noteninja.noteninjabackend.exception.EmailAlreadyTaken;
import com.noteninja.noteninjabackend.exception.TokenRefreshException;
import com.noteninja.noteninjabackend.exception.UsernameAlreadyTakenException;
import com.noteninja.noteninjabackend.model.ERole;
import com.noteninja.noteninjabackend.model.entity.RefreshToken;
import com.noteninja.noteninjabackend.model.entity.Role;
import com.noteninja.noteninjabackend.model.entity.User;
import com.noteninja.noteninjabackend.model.request.LoginRequest;
import com.noteninja.noteninjabackend.model.request.SignupRequest;
import com.noteninja.noteninjabackend.model.request.TokenRefreshRequest;
import com.noteninja.noteninjabackend.model.response.Response;
import com.noteninja.noteninjabackend.model.response.TokenRefreshResponse;
import com.noteninja.noteninjabackend.repository.RoleRepository;
import com.noteninja.noteninjabackend.repository.UserRepository;
import com.noteninja.noteninjabackend.security.UserDetailsImpl;
import com.noteninja.noteninjabackend.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;


    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @PostMapping("/signup")
    public Response registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.username())) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new EmailAlreadyTaken("Email is already in use");
        }

        // Create new user's account
        User user = User.builder()
                .username(signUpRequest.username())
                .email(signUpRequest.email())
                .password(encoder.encode(signUpRequest.password()))
                .build();

        Set<String> strRoles = signUpRequest.role();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return Response.builder()


                .build();
    }
    @PostMapping("/signin")
    public Response authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .data(Map.of("jwt",jwt,"refresh_token",refreshToken.getToken(),"user_id",userDetails.getId(),"email", userDetails.getEmail(),"username", userDetails.getUsername(),"roles",roles))
                .build();

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.refreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}