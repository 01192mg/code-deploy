package com.example.codedeploy.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.codedeploy.security.MemberDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.example.codedeploy.jwt.JwtProperties.*;

@Component
public final class JwtService {

    @Value("${jwt.secret}")
    public String SECRET_KEY;

    public String CreateAccessToken(MemberDetails memberDetails) {
        long now = System.currentTimeMillis();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .withClaim("username", memberDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getClaim(String claim, String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token)
                .getClaim(claim).asString();
    }
}
