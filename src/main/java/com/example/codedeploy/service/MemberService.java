package com.example.codedeploy.service;

import com.example.codedeploy.dto.request.MemberRequestDto;
import com.example.codedeploy.model.Member;
import com.example.codedeploy.repository.MemberRepository;
import com.example.codedeploy.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(MemberRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        System.out.println("username = " + username);
        System.out.println("password = " + password);
        Member member = new Member(username, password);

        memberRepository.save(member);
    }

}
