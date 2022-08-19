package com.example.codedeploy.controller;

import com.example.codedeploy.dto.request.MemberRequestDto;
import com.example.codedeploy.dto.response.ResponseDto;
import com.example.codedeploy.security.MemberDetails;
import com.example.codedeploy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/api/info")
    public void info(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails == null) {
            System.out.println("memberDetails == null");
        }
        System.out.println(memberDetails.getUsername());

    }

    @GetMapping("test")
    public ResponseDto<?> test() {
        return ResponseDto.success("test");
    }
}
