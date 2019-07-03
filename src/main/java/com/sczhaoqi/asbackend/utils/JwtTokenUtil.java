package com.sczhaoqi.asbackend.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sczhaoqi.asbackend.domain.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.Date;

/**
 * @author sczhaoqi
 * @date 2019/5/4 14:22
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenUtil
{

    @Autowired
    private CacheManager cacheManager;
    /**
     * header名称
     */
    private String tokenHeader;

    /**
     * token前缀
     */
    private String tokenPrefix;
    /**
     * 秘钥
     */
    private String secret;

    private String issuer;

    /**
     * 过期时间
     */
    private String expiration;

    /**
     * 选择记住后过期时间
     */
    private String rememberExpiration;

    private Algorithm algorithm;
    private String userKey = "user";

    private Algorithm getAlgorithm()
    {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC512(secret);
        }
        return algorithm;
    }

    /**
     * 生成token
     *
     * @return
     */
    public String createToken(User user, Boolean rememberMe)
    {
        Duration expiredDuration = Duration.parse(rememberMe ? this.rememberExpiration : this.expiration);
        Long time = expiredDuration == null ? 3600 * 1000 : expiredDuration.toMillis();
        String oriToken = JWT.create()
                .withClaim("user", JSON.toJSONString(user))
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + time * 1000))
                .sign(getAlgorithm());
        String newToken = DigestUtils.md5DigestAsHex(oriToken.getBytes());
        cacheManager.getCache("sysCache").put(newToken, oriToken);
        cacheManager.getCache("sysCache").put(user.getUsername(), newToken);
        return newToken;
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public String getUsername(String token)
    {
        return getUser(token).getUsername();
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public DecodedJWT parseToken(String token)
    {
        // verify
        verifyToken(token);
        return JWT.decode(token);
    }

    public boolean verifyToken(String token)
    {

        try {
            JWT.require(getAlgorithm())
                    .build().verify(token);
            return true;
        }
        catch (JWTVerificationException exception) {
            //Invalid signature/claims
            log.error("Invalid signature/claims");
            return false;
        }
    }

    public String oriToken(String token)
    {
        assert token != null;
        return cacheManager.getCache("sysCache").get(token, String.class);
    }

    /**
     * 获取user
     *
     * @param token
     * @return
     */
    public User getUser(String token)
    {
        ;
        return JSON.parseObject(parseToken(cacheManager.getCache("sysCache").get(token, String.class)).getClaim(userKey).asString(), User.class);
    }
}
