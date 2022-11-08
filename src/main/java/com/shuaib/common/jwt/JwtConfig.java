package com.shuaib.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * JWT的token，区分大小写
 */
@ConfigurationProperties(prefix = "config.jwt")
@Component
@Data
public class JwtConfig {

    private String secret;
    private Long expire;
    private String header;

    /**
     * 生成token
     * @param subject 签发token依据的字符串
     */
    public String createToken(String subject) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);//过期时间

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取token中注册信息
     * @param token token值
     * @return claims签证
     */
    public Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); //防止jwt过期解析报错
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token是否过期失效
     * @param expirationTime 过期时间
     */
    public boolean isTokenExpired(Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 获取token失效时间
     * @param token token值
     * @return token
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }

    /**
     * 获取用户名从token中
     */
    public String getUserIdFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }

    /**
     * 设置token过期时间
     * @param expire 过期时间(毫秒)
     */
    public void setExpire(Long expire) {
        this.expire = expire;
    }
}

