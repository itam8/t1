package org.example.clientprocessing.service;

import org.example.clientprocessing.model.User;
import org.example.clientprocessing.model.UserDetailsImpl;
import org.example.clientprocessing.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername_withValidUsername() {
        User user = new User();
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findByLogin(user.getLogin())).thenReturn(optionalUser);

        UserDetails expectedUserDetails = UserDetailsImpl.build(user);
        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(user.getLogin());

        Assertions.assertEquals(expectedUserDetails.getUsername(), actualUserDetails.getUsername());
        Assertions.assertEquals(expectedUserDetails.getPassword(), actualUserDetails.getPassword());
        Assertions.assertEquals(expectedUserDetails.getAuthorities(), actualUserDetails.getAuthorities());
    }

    @Test
    void loadUserByUsername_withInvalidUsername() {
        User user = new User();
        Optional<User> optionalUser = Optional.empty();

        Mockito.when(userRepository.findByLogin(user.getLogin())).thenReturn(optionalUser);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(user.getLogin()));
    }
}
