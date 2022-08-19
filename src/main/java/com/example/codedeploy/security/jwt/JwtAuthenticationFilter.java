package com.example.codedeploy.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.codedeploy.dto.request.MemberRequestDto;
import com.example.codedeploy.security.MemberDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.setFilterProcessesUrl("/members/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        MemberRequestDto memberRequestDto = requestToMemberRequestDto(request);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberRequestDto.getUsername(), memberRequestDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    // 요청 성공 시 responseHeader에 token을 담아줌
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) {
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        String accessToken = jwtService.CreateAccessToken(memberDetails);

        response.addHeader(JwtProperties.AUTHORIZATION_HEADER, JwtProperties.TOKEN_PREFIX + accessToken);
    }

    private MemberRequestDto requestToMemberRequestDto(HttpServletRequest request) {
        ObjectMapper om = new ObjectMapper();
        MemberRequestDto memberRequestDto;
        try {
            memberRequestDto = om.readValue(request.getInputStream(), MemberRequestDto.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return memberRequestDto;
    }
}
