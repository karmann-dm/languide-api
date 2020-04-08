package com.karmanno.languagelearningappapi.service;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dto.LoginInfo;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.repository.UserRepository;
import com.karmanno.languagelearningappapi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signUp(String username, String password, Language language) {
        if(userRepository.existsByName(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User()
                .setName(username)
                .setPassword(passwordEncoder.encode(password))
                .setLanguage(language);

        return userRepository.save(user);
    }

    @Override
    public LoginInfo logIn(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        User user = userRepository.findByName(username)
                .orElseThrow(() ->
                        new RuntimeException("User with name = " + username + " does not exist"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return new LoginInfo()
                .setToken(jwt)
                .setId(user.getId())
                .setUsername(user.getName())
                .setLanguage(user.getLanguage().toString());
    }
}
