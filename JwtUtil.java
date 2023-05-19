// package ml.zephon.utils;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.gson.io.GsonSerializer;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * Jwt工具类，依赖于
 *         <dependency>
 *             <groupId>io.jsonwebtoken</groupId>
 *             <artifactId>jjwt-api</artifactId>
 *             <version>0.11.5</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>io.jsonwebtoken</groupId>
 *             <artifactId>jjwt-impl</artifactId>
 *             <version>0.11.5</version>
 *             <scope>runtime</scope>
 *         </dependency>
 *         <dependency>
 *             <groupId>io.jsonwebtoken</groupId>
 *             <artifactId>jjwt-gson</artifactId>
 *             <version>0.11.5</version>
 *         </dependency>
 */
public class JwtUtil {
    private static final String SECRET = "secret-secret-secret-secret-secret-secret-secret";

    /**
     * 生成token
     * @param content 要保存在token中的信息
     * @return 返回生成的token
     */
    public static String generateToken(String content) {

        byte[] encode = Base64.getEncoder().encode(SECRET.getBytes());
        return Jwts.builder().
                setSubject(content)
                .signWith(new SecretKeySpec(encode, 0, encode.length, "HmacSHA256"),
                        SignatureAlgorithm.HS256)
                .serializeToJsonWith(new GsonSerializer<>(new Gson())).compact();
    }

    /**
     * 生成会过期的token
     * @param content 要保存在token中的信息
     * @param expire_time token过期时间
     * @return 返回生成的token
     */
    public static String generateToken(String content, Long expire_time) {
        byte[] encode = Base64.getEncoder().encode(SECRET.getBytes());
        return Jwts.builder().
                setSubject(content)
                .signWith(new SecretKeySpec(encode, 0, encode.length, "HmacSHA256"),
                        SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + expire_time))
                .serializeToJsonWith(new GsonSerializer<>(new Gson())).compact();
    }

    /**
     * 解析token
     * @param token token字符串
     * @return 解析的subject
     */
    public static Object parseToken(String token) {
        byte[] encode = Base64.getEncoder().encode(SECRET.getBytes());
        String subject = Jwts.parserBuilder().setSigningKey(new SecretKeySpec(encode, 0, encode.length, "HmacSHA256"))
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
        return subject;
    }

    public static void main(String[] args) {
        String t = "测试";
        String token = generateToken(t);
        System.out.println(token);
        System.out.println("解析:" + parseToken(token));
    }
}
