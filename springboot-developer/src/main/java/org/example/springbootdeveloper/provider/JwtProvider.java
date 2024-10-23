package org.example.springbootdeveloper.provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/*
    JwtProvider 클래스
    : JWT 토큰을 생성하고 검증하는 역할
    - HS256 암호화 알고리즘을 사용하여 JWT 서명
    - 비밀키는 Base64로 인코딩 지정 - 환경변수(jwt.secret)
    - JWT 만료 기간은 10시간 지정 - 환경변수(jwt.expiration)
*/

@Component // 스프링 컨테이너에서 해당 클래스를 빈으로 관리하기 위해 사용
// cf) @Bean: 메서드 레벨에서 선언, 반환되는 객체를 개발자가 수동으로 빈 등록
//     @Component: 클래스 레벨에서 선언, 스프링 런타임 시 컴포넌트 스캔을 통해 자동으로 빈을 찾고 등록하는 어노테이션(의존성 주입)
public class JwtProvider {

    private final Key key; // JWT 서명에 사용할 암호화 키
    private final int jwtExpirationMs; // JWT 토큰의 만료 시간을 저장

    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.jwtExpiration}") int jwtExpirationMs) {
        // 생성자; JWTProvider 객체를 생성할 때 비밀키와 만료 시간을 초기와

        // Base64로 인코딩된 비밀키를 디코딩하여 HMAC-SHA 알고리즘으로 암호화된 키 생성
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        
        // 환경 변수에서 가져온 말료 시간을 변수에 저장
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /*
    * JWT 생성 메서드
    * @param: 사용자 식별자(JWT(의) subject(로) 설정됨)
    * @return: 생성된 JWT 토큰 문자열
    */
    public String generateJwtToken(String userId) {
        // 만료시간: 현재시간 + 10시간
        Date expirationDate = new Date(new Date().getTime() + jwtExpirationMs);

        String jwt = null; // 토큰을 저장할 변수 선언

        try {
            // 비밀키를 사용하여 HMAC-SHA 키 생성
            // - Keys.hmacShaKeyFor: 환경설정에 있는 비밀키 문자열을 바이트배열로 전달하며 SecretKey 인스턴스를 생성
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            // JWT 생성
            // subject(사용자 ID), 발행 시간, 만료시간, 서명 알고리즘 포함
            jwt = Jwts.builder()
                    .setSubject(userId) // JWT(의) subject 필드에 사용자 ID 설정
                    .setIssuedAt(new Date()) // 토큰 발생 시간 설정
                    .setExpiration(expirationDate) // 만료 시간 설정
                    .signWith(key, SignatureAlgorithm.HS256) // HMAC-SHA256 알고리즘으로 서명
                    .compact(); // 최종적으로 JWT 문자열로 컴팩트하게 변환

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return jwt;
    }


    /*
     * JWT 검증 메서드
     * @param: jwt(를) 검증할 JWT 문자열
     * @return: JWT(가) 유요하다면 userID(사용자 식별자)를 반환, 그렇지 않으면 null 반환
     */
    public String validate(String jwt) {
        String userId = null;

        try {
            // 비밀키를 사용하여 JWT 암호화의 사용할 키 생성
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            // JWT 파싱 및 검증
            // : 서명 검증 후 payload(의) subject(userId)를 추출
            userId = Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 검증에 사용할 비밀키 설정
                    .build()
                    .parseClaimsJws(jwt)// JWT(를) 파싱하고 JWS(Signature 포함) 검증
                    .getBody()// JWT(의) payload(body)를 가져옴
                    .getSubject(); // payload(의) subject(를) 반환

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return userId; // JWT(가) 유효하다면 userId 반환
    }

    public boolean isValidToken(String token) {
        return true;
    }
}
