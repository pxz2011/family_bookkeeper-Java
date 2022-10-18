//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pxh.hab.entity.User;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JWTUtil {
    private static final String TOKENKEY = "i'amtoken!xlhtk!@#$%^&*()";
    static User user = new User();
    @Resource
    RedisUtil redisUtil;

    public static String getToken(String userName, String Password) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        String Token = JWT.create().withClaim("username", userName).withClaim("password", Password).withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256("i'amtoken!xlhtk!@#$%^&*()"));
        System.out.println(Token);
        return Token;
    }

    public  boolean verify(String token) {
        System.out.println("验证token" + JWT.require(Algorithm.HMAC256(TOKENKEY)).build().verify(token));
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKENKEY)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
            System.out.println("username="+jwt.getClaim("username").asString());
            String redisToken = redisUtil.get("token_" + jwt.getClaim("username").asString());
            System.out.println("redisToken="+redisToken);
            System.out.println(redisToken.equals(token));
            return redisToken.equals(token);
        } catch (Exception e) {
            return false;
        }

    }

    public static User parse(String token) throws Exception {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("i'amtoken!xlhtk!@#$%^&*()")).build();
            DecodedJWT verify = jwtVerifier.verify(token);
            String userName = verify.getClaim("username").asString();
            String password = verify.getClaim("password").asString();
            System.out.println("userName = " + userName);
            System.out.println("password = " + password);
            if (userName == null || password == null) {
                return null;
            }

            user.setUserName(userName);
            user.setPassword(password);
        } catch (TokenExpiredException var5) {
            throw new Exception("token已失效!!,请重新登录!!", var5);
        } catch (JWTDecodeException | SignatureVerificationException var6) {
            throw new Exception("token错误!", var6);
        }

        return user;
    }
}
