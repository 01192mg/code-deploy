package com.example.codedeploy.jwt;

import com.example.codedeploy.model.Member;
import com.example.codedeploy.repository.MemberRepository;
import com.example.codedeploy.security.MemberDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService, MemberRepository memberRepository) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // token 검증
            String token = jwtService.resolveToken(request);
            String username = jwtService.getClaim("username", token);

            // Authentication 생성
            Member member = memberRepository.findByUsername(username).orElseThrow();
            MemberDetails memberDetails = new MemberDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());

            // Authentication 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (Exception e) {
            chain.doFilter(request, response);
        }
    }
}
