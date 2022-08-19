package com.example.codedeploy.jwt;

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

import lombok.RequiredArgsConstructor;

import static com.example.codedeploy.jwt.JwtProperties.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // 로그인 요청 ([POST] "/login")
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
                                            Authentication authResult) {
        MemberDetails memberDetails = (MemberDetails) authResult.getPrincipal();
        String accessToken = jwtService.CreateAccessToken(memberDetails);

        response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + accessToken);
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
