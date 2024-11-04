package org.example.이론;

public class w_스프링시큐리티_보충 {
    /*
        Spring Boot Security: "자물쇠처럼 사이트의 정보를 보호"

        1. Spring Security(가) 하는 일
            - "로그인과 같은 자물쇠를 만드는 도구"

            1) 인증(Authentication)
                : 누가 들어오려고 하는지 확인하는 과정
                    - "이 사람이 정말 이 사람이 맞나요?"라는 질문

            2) 인가, 권한(Authorization)
                : 들어오려는 사람이 무슨 일을 할 수 있는지 결정
                    - 접 안에 들어온 사람이 어떤 방까지 들어갈 수 있는지 허락하는 것

       2. Spring Security(의) 동작 방식
        - Filter(필터)
            : 모든 요청(Request)을 검사
            EX) 놀이공원 입구에서 티켓을 검사하는 사람

        - Authentication Manager(인증 관리자)
            : 위의 필터를 통과하면 Spring Security(의) "인증 관리자"가 ID와 비밀번호가 맞는지 확인
            EX) 티켓 인증이 완료되면 "인증 토큰"을 발급

        - Security Context
            : 입장한 사람의 정보를 저장(담기)
            EX) 놀이공원에서 입장 고객에 대한 정보를 저장, 놀이기구 이용 제한을 확인

     ===== WebSecurityConfig 클래스 =====
        1. corsFilter 메서드
            CORS 정책 적용: 외부 도메인에서의 API 접근을 허용
            모든 출처 허용: 모든 도메인에서의 요청을 허용하도록 설정
            모든 HTTP 메서드 허용: GET, POST, PUT, DELETE 등의 모든 메서드를 허용
            필터 반환: 설정된 CORS 필터를 반환하여 다른 요청에서도 적용되도록 함

        2. filterChain 메서드
            CSRF 보호 비활성화: REST API 특성상 불필요한 CSRF 보호를 비활성화
            CORS 설정 활성화: 앞서 정의한 CORS 필터 설정을 활성화
            특정 경로 접근 허용: 인증 없이 접근 가능한 경로를 지정
            JWT 필터 추가: 인증을 위해 JWT 검증 필터를 추가

        3. authenticationManager 메서드
            인증 관리 기능 제공: 사용자 인증을 처리할 수 있는 AuthenticationManager(를) 생성
            DaoAuthenticationProvider 사용: DB(에서) 사용자를 인증하는 프로바이더를 사용
            BCrypt 비밀번호 암호화: 비밀번호 검증 시 암호화를 통해 비교
            ProviderManager 반환: 다중 인증 프로바이더 관리자를 반환하여 인증 요청을 처리

        4. bCryptpasswordEncoder 메서드
            비밀번호 암호화: 비밀번호를 암호화하는 인코더를 제공
            BCrypt 사용: 단방향 암호화 알고리즘인 BCrypt(를) 사용
            보안 강화: 암호화된 비밀번호는 복호화할 수 없으므로 보안성이 높음
            Bean 등록: Spring(에서) 관리하는 Bean(으로) 등록되어 필요 시 자동으로 주입
    */
}
