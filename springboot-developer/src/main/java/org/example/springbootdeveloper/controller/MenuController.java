package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.common.constant.ApiMappingPattern;
import org.example.springbootdeveloper.dto.request.MenuRequestDto;
import org.example.springbootdeveloper.dto.response.MenuResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.MENU)
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // MenuController mapping pattern 설정
    public static final String MENU_POST = "/";

    public static final String MENU_GET_MENU_ID = "/{menuId}";
    public static final String MENU_GET_LIST = "/list";
    // public static final String MENU_GET_CATEGORY= "/{menuCategory}";

    public static final String MENU_PUT = "/{menuId}";

    public static final String MENU_DELETE = "/{menuId}";

    @PostMapping(MENU_POST)
    public ResponseEntity<ResponseDto<MenuResponseDto>> createMenu(@Validated @RequestBody MenuRequestDto dto) {
        ResponseDto<MenuResponseDto> result = menuService.createMenu(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(MENU_GET_LIST)
    public ResponseEntity<ResponseDto<List<MenuResponseDto>>> getAllMenus() {
       ResponseDto<List<MenuResponseDto>> result = menuService.getAllMenus();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(MENU_GET_MENU_ID)
    public ResponseEntity<ResponseDto<MenuResponseDto>> getMenuById(@PathVariable Long id) {
        ResponseDto<MenuResponseDto> retsult = menuService.getMenuById(id);
        return ResponseEntity.status(HttpStatus.OK).body(retsult);
    }


}
