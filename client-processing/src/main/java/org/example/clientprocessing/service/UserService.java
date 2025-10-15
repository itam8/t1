package org.example.clientprocessing.service;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.dto.JwtResponse;
import org.example.clientprocessing.dto.LoginDto;
import org.example.clientprocessing.model.Role;
import org.example.clientprocessing.model.RoleEnum;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.model.UserDetailsImpl;
import org.example.clientprocessing.repository.UserRepository;
import org.example.clientprocessing.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> authenticateUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }

    public User create(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalStateException("Пользователь с таким логином уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleService.findByName(RoleEnum.ROLE_CURRENT_CLIENT);
        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }

    public User findByDocumentId(String documentId) {
        return userRepository.findByDocumentId(documentId);
    }
}
