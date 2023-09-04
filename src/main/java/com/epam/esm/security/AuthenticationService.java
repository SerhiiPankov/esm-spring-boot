package com.epam.esm.security;

import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.dto.UserSignInRequestDto;
import com.epam.esm.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public UserLoginResponseDto authenticate(UserSignInRequestDto requestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestDto.getEmail(), requestDto.getPassword()));
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(requestDto.getEmail());
        return new UserLoginResponseDto(jwtService.generateToken(userDetails));
    }

}
