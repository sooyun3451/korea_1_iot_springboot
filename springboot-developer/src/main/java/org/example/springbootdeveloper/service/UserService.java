package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.dto.request.UserLoginRequestDto;
import org.example.springbootdeveloper.dto.request.UserRequestDto;
import org.example.springbootdeveloper.entity.User;
import org.example.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    // 비즈니스 로직
    // Controller 요청을 받아 필요한 데이터를 Repository(를) 통해 얻거나 전달하고 기능 구현 후 응답을 Controller(에게) 전달
    // +) 기능 구현에 있어 필요한 보안을 설정

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입
    public String signup(UserRequestDto userRequestDto) {
        try {
            // 중복되는 이메일 검증
            if (userRepository.existsByEmail(userRequestDto.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            // 패스워드 암호화
            String encodedPassword = bCryptPasswordEncoder.encode(userRequestDto.getPassword());

            User user = User.builder()
                    .email(userRequestDto.getEmail())
                    .password(encodedPassword)
                    .build();

            userRepository.save(user);

            return "회원가입이 성공적으로 완료되었습니다.";

        } catch(Exception e) {
            return "회원가입에 실패하였습니다: " + e.getMessage();
        }
    }

    public String login(UserLoginRequestDto userLoginRequestDto) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
