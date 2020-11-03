package com.topjia.music.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author wjh
 * @date 2020-06-02 16:57
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtOperator {
    /**
     * 密钥
     */
    @Value("${secret:aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrssstttuuuvvvwwwxxxyyyzzz}")
    private String secret;

    /**
     * 有效期
     * 默认值 1 天 86400000
     */
    @Value("${expire-time-in-second:86400000}")
    private Long expirationTimeInSecond;

    /**
     * 通过token获取claim
     *
     * @param token token
     * @return claim
     */
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("token解析错误", e);
            throw new IllegalArgumentException("Token invalided");
        }
    }

    /**
     * 通过token获取过期时间
     *
     * @param token token
     * @return 过期时间
     */
    public Date getExpiationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Date getExpirationTime() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        return new Date(System.currentTimeMillis() + this.expirationTimeInSecond);
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     * @return 已过期返回true，未过期返回false
     */
    private Boolean isTokenExpired(String token) {
        Date expiation = getExpiationDateFromToken(token);
        return expiation.before(new Date());
    }

    public String generateToken(Map<String, Object> claims) {
        Date createdTime = new Date();
        Date expirationTime = this.getExpirationTime();

        byte[] keyBytes = secret.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdTime)
                .setExpiration(expirationTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 校验token是否合法
     *
     * @param token token
     * @return 校验通过返回true，校验失败返回false
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
