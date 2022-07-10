package test.artplancom.TestTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import test.artplancom.TestTask.config.jwt.JwtUtils;
import test.artplancom.TestTask.model.*;
import test.artplancom.TestTask.model.payload.AuthRequest;
import test.artplancom.TestTask.model.payload.AuthResponse;
import test.artplancom.TestTask.model.enums.ERole;
import test.artplancom.TestTask.repository.RoleRepository;
import test.artplancom.TestTask.repository.UserRepository;
import test.artplancom.TestTask.service.security.UserDetailsImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/home/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    JwtUtils jwtUtils;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body(new AuthResponse(jwt));
    }
    
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest authRequest) {
        if (userRepository.existsByUsername(authRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is exist");
        }
        
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        
        User user = new User(authRequest.getUsername(),
                passwordEncoder.encode(authRequest.getPassword()),
                new ArrayList<Animal>());
        user.setRoles(roles);
        userRepository.save(user);
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
    
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body(new AuthResponse(jwt));
    }
}
