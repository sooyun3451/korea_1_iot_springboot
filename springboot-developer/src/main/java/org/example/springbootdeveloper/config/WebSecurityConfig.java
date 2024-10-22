package org.example.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

// 웹 보안 구성(설정)
@Configuration // 해당 클래스가 설정 클래스로 사용됨을 명시
@EnableWebSecurity // Spring Security(의) 웹 보안을 활성화
// @RequiredArgsConstructor // final 필드 | @NonNull 필드에 대해 생성자를 자동 생성
public class WebSecurityConfig {

    @Autowired
    private @Lazy UserService userService;
    // @Lazy: 사용할때만 활성화하기 위해서 늦춰두기

    @Bean
    // 정적 리소스나 특정 URL(에) 대해 Spring Security(가) 보안 검사를 무시하도록 설정: 기능 비활성화
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                // H2 콘솔에 대한 접근 보안 검사를 무시하도록 설정
                .requestMatchers(toH2Console())
                // "/static/**" 경로의 정적 리소스를 보안 검사에서 제외
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 쿠키를 허용할지 여부
        config.addAllowedOriginPattern("*"); // 모든 도메인 허용
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 적용
        return new CorsFilter(source);
    }

    // 보안 필터 체인 정의: 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth // 인증, 인가 설정
                        .requestMatchers(
                                // 특정 경로에 대한 엑세스 설정
                                // .requestMatchers(): 특정 요청과 일치하는 url(에) 대한 엑세스
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        )
                        // .permitAll(): 누구나 접근이 가능하게 설정, /login, /signup, /user 요청이 오면 인증, 인가 없이도 접근 가능
                        .permitAll()
                        // .anyRequest(): 위에서 설정한 url 이외의 요청에 대해 설정
                        // .authenticated(): 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근 가능
                        .anyRequest().authenticated())
                // cf) csrf(Cross-Site Request Forgery): 사이트 간 요청 위조의 줄임말
                // cf) csrf 공격을 방지하기 위해 활성화하는 것을 권장
                .csrf(AbstractHttpConfigurer::disable) // 비활성화
                .build();
    }

    // 인증 처리
    // AuthenticationManager: Spring Security 에서 인증(Authentication)을 담당하는 핵심 인터페잇
    // >> 인증 과정에서 사용자 자격 증명(EX. username, password)을 확인하고 올바르면 인증 토큰을 반환
    @Bean
    // 인증 관리자 관련 설정
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            // : 비밀번호를 암호화하는 BCryptPasswordEncoder Bean(을) 주입
            BCryptPasswordEncoder bCryptPasswordEncoder, // 비 크립트 페스워드 인코더
           // 사용자 세부 정보를 제공하는 UserDetailsService Bean(을) 주입
            UserDetailsService userDetailsService,
            UserService userService
    ) throws Exception{
        // DaoAuthenticationProvider: DB(에서) 사용자 인증을 처리
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // .setUserDetailsService: 사용자 세부 정보를 가져오는 서비스 설정
        authProvider.setUserDetailsService(userDetailsService);
        // 비밀번호 암호화를 사용
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        // ProviderManager: DaoAuthenticationProvider 인증 처리
        return new ProviderManager(authProvider);
    }

    @Bean
    // 비밀번호 암호화: Spring 컨텍스트에 Bean(으로) 등록하여 사용, 단방향 해시함수를 사용하여 비밀번호를 암호화 함
    // >> 복호화할 수 없음!!
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
