package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.common.constant.ApiMappingPattern;
import org.example.springbootdeveloper.common.constant.ResponseMessage;
import org.example.springbootdeveloper.dto.response.GetUserResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiMappingPattern.USER)
@RequiredArgsConstructor
// final, @NonNull 설정 된 필드를 필수 매개변수로 하는 생성자를 만드는 어노테이션
public class UserController {

    // 1. 생성자 의존성 주입 방식
    private final @Lazy UserService userService;

   // public userController(UserService userService) {
   //     this.userService = userService;
   // }

    // 2. 필드 의존성 주입 방식
   // @Autowired
   // private UserService userService;

    // == UserController mapping pattern 설정 ==
    public static final String USER_GET_USER_EMAIL = "/";

    // 사용자 데이터 가져오기(단건)
    @GetMapping(USER_GET_USER_EMAIL)
    public ResponseEntity<ResponseDto<GetUserResponseDto>> getUser(@AuthenticationPrincipal String userEmail) {
        if(userEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.setFailed(ResponseMessage.AUTHENTICATION_FAIL));
        }
        ResponseDto<GetUserResponseDto> result = userService.findUserByEmail(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
