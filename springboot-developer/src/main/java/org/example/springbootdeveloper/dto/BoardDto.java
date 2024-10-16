package org.example.springbootdeveloper.dto;

import lombok.*;
import org.example.springbootdeveloper.entity.Category;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private Category category;
}
