package org.example.이론;

import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class _보충 {
    /*
        @PathVariable
        : URL 경로의 일부를 변수로 받아올 때 사용
        - 특정 사용자, 게시물, 제품 등의 ID를 URL로 전달받아 조회, 수정, 삭제할 때 사용

        @RequestParam
        : URL 쿼리 파라미터를 받아올 때 사용
        - 페이징, 검색어 필터링, 정렬 등의 기능에 사용

        @RequestBody
        : JSON 형태의 데이터를 요청 본문으로 전달받을 때 사용
        - 새로운 데이터 생성을 위한 POST | 데이터를 수정하는 PUT 요청에 주로 사용

        +) @AuthenticationPrincipal
        : 인증된 사용자 정보를 가져오기 위해 사용
        - Spring Security에서 로그인한 사용자의 정보를 컨트롤러에서 쉽게
            사용할 수 있도록 하는 기능
    */

    // @PathVariable
    @GetMapping("/categories/{categoryId}/items/{itemId}")
    public ResponseEntity<?> getItemInCategory(
            @PathVariable Long categoryId,
            @PathVariable Long itemId
    ) {
        // DTO타입 item = categoryService.getItemInCategory(categoryId, itemId);
        return ResponseEntity.ok(null);
    }

    // @RequestParam
    @GetMapping("/search")
    // query가 들어간 사용자 조회
    // + 조회 건수를 limit으로 지정

    // cf) @RequestParam(defaultValue = "기본값")
    public ResponseEntity<List<?>> searchUsers(
            @RequestParam String query,
            @RequestParam(defaultValue = "10") int limit
    ) {
//        List<제네릭타입> users = userService.searchUsers(query, limit);
        return ResponseEntity.ok(null);
    }

    // @RequestBody
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @RequestBody ResponseDto data
    ) {
        // 제네릭타입 updatedProduct = productService.updateProduct(productId, data);
        return ResponseEntity.ok(null);
    }

    // @AuthenticationPrincipal
    @GetMapping("/orders")
    // 로그인 한 사용자의 주문 목록 페이징 조회
    public ResponseEntity<?> getUserOrders(
            @RequestParam int page, // 몇 번째 페이지에
            @RequestParam int size, // 몇 개의 주문 목록

            // cf) @AuthenticationPrincipal의 데이터 타입은
            //      Spring Security에서 토큰을 추출하고
            //      SecurityContext에 저장할 데이터를 지정 가능
            @AuthenticationPrincipal Long id
    ) {
        // List<제네릭 타입> orders = orderService.getUserOrders(id, page, size);
        return ResponseEntity.ok(null);
    }
}