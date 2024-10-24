package org.example.springbootdeveloper.service;

import org.example.springbootdeveloper.dto.request.MenuRequestDto;
import org.example.springbootdeveloper.dto.response.MenuResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    public ResponseDto<MenuResponseDto> createMenu(MenuRequestDto dto) {
        return null;
    }

    public ResponseDto<List<MenuResponseDto>> getAllMenus() {
        return null;
    }

    public ResponseDto<MenuResponseDto> getMenuById(Long id) {
        return null;
    }
}
