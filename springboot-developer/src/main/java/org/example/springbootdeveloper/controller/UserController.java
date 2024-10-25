package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.common.constant.ApiMappingPattern;
import org.example.springbootdeveloper.service.UserService;
import org.springframework.context.annotation.Lazy;
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


}
