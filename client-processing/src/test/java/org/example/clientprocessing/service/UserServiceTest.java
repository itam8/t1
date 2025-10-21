package org.example.clientprocessing.service;

import org.example.clientprocessing.dto.JwtResponse;
import org.example.clientprocessing.dto.LoginDto;
import org.example.clientprocessing.model.Role;
import org.example.clientprocessing.model.RoleEnum;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.model.UserDetailsImpl;
import org.example.clientprocessing.repository.UserRepository;
import org.example.clientprocessing.util.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void authenticateUser() {
        LoginDto loginDto = new LoginDto("testUser", "password");
        UserDetailsImpl userDetails =
                new UserDetailsImpl(1L, "testUser", "test@test.com", "password", Collections.emptyList());
        String jwt = "jwt";

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwt);

        ResponseEntity<?> response = userService.authenticateUser(loginDto);
        JwtResponse actualJwtResponse = (JwtResponse) response.getBody();

        Assertions.assertEquals(jwt, actualJwtResponse.getToken());
        Assertions.assertEquals(userDetails.getId(), actualJwtResponse.getId());
        Assertions.assertEquals(userDetails.getUsername(), actualJwtResponse.getUsername());
        Assertions.assertEquals(userDetails.getEmail(), actualJwtResponse.getEmail());
    }

    @Test
    void create_withValidLogin() {
        User user = new User();
        Role role = new Role();
        role.setName(RoleEnum.ROLE_CURRENT_CLIENT);
        user.setPassword("password");
        user.setRoles(Set.of(role));

        Mockito.when(userRepository.existsByLogin(user.getLogin())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        Mockito.when(roleService.findByName(role.getName())).thenReturn(role);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(user, userService.create(user));
    }

    @Test
    void create_withInvalidLogin() {
        User user = new User();

        Mockito.when(userRepository.existsByLogin(user.getLogin())).thenReturn(true);

        Assertions.assertThrows(IllegalStateException.class, () -> userService.create(user));
    }

    @Test
    void findByDocumentId() {
        User user = new User();
        String documentId = "documentId";

        Mockito.when(userRepository.findByDocumentId(documentId)).thenReturn(user);

        Assertions.assertEquals(user, userService.findByDocumentId(documentId));
    }
}
