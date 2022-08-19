package com.example.codedeploy.jwt;

public interface JwtProperties {
    String TOKEN_PREFIX = "Bearer ";
    String AUTHORIZATION_HEADER = "Authorization";
    String ACCESS_TOKEN_SUBJECT = "accessToken";
    String CLAIM_USERNAME = "username";
    long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;// 30분
}
