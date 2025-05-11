package com.moncabinetdentaire.Controller;

import com.moncabinetdentaire.dto.AuthenticationRequest;
import com.moncabinetdentaire.dto.AuthenticationResponse;
import com.moncabinetdentaire.dto.SignupRequest;
import com.moncabinetdentaire.dto.UserDto;
import com.moncabinetdentaire.entities.User;
import com.moncabinetdentaire.repositories.UserRepositories;
import com.moncabinetdentaire.service.auth.AuthService;

import com.moncabinetdentaire.service.jwt.UserService;
import com.moncabinetdentaire.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UserRepositories userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // Récupérer tous les utilisateurs
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            var users = userRepository.findAll();

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun utilisateur trouvé");
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des utilisateurs");
        }
    }

    // Inscription d'un nouvel utilisateur
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("l'utilisateur existe déjà avec cet email");
        }
        UserDto createdUserDto = authService.signupUser(signupRequest);
        if (createdUserDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("utilisateur non créé");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    // Connexion d'un utilisateur
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("username ou mot de passe incorrect");
        }
        final UserDetails userDetails = userService.userDetailService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());

        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setName(optionalUser.get().getName());
        }

        return ResponseEntity.ok(authenticationResponse);
    }

    // Modifier un utilisateur
    @PutMapping("/users/{userId}")
    public ResponseEntity<?> editUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        User user = userOptional.get();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setUserRole(userDto.getUserRole());
        // Mettez à jour d'autres champs nécessaires ici

        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur mis à jour avec succès");
    }

    // Supprimer un utilisateur
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        userRepository.deleteById(userId);

        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }
}
