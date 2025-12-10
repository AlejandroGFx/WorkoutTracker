package com.example.workouttracker.Controller;

import com.example.workouttracker.DTO.LoginRequestDTO;
import com.example.workouttracker.DTO.LoginResponseDTO;
import com.example.workouttracker.DTO.RegisterRequestDTO;
import com.example.workouttracker.Entity.User;
import com.example.workouttracker.Enum.UserRole;
import com.example.workouttracker.Repository.UserRepository;
import com.example.workouttracker.Security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
class AuthenticationController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticationController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> signIn(@RequestBody RegisterRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        User newUser = new User();
        newUser.setUsername(requestDTO.getUsername());
        newUser.setPassword(requestDTO.getPassword());
        newUser.setEmail(requestDTO.getEmail());
        newUser.setRole(UserRole.USER);
        newUser.setFirstName(requestDTO.getFirstName());
        newUser.setLastName(requestDTO.getLastName());
        this.userRepository.save(newUser);
        return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
        if (!this.userRepository.existsUserByUsernameAndPasswordAndEmail(
                requestDTO.getUsername(), requestDTO.getPassword(), requestDTO.getEmail()
        )) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = this.userRepository.findByUsername(requestDTO.getUsername());
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(jwtService.generateToken(user), jwtService.getExpirationMs());
        return ResponseEntity.ok().body(loginResponseDTO);
    }
}
