package com.example.codedeploy.controller;

import com.example.codedeploy.dto.request.MemberRequestDto;
import com.example.codedeploy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public void signup(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.signup(memberRequestDto);
    }
}
