package org.example.springbootdeveloper.dto.request;

import jakarta.annotation.Nonnull;
import lombok.*;

@Data
@NoArgsConstructor
public class MenuRequestDto {
    @Nonnull
    private String name;
    @Nonnull
    private String description;
    @Nonnull
    private int price;
    @Nonnull
    private boolean isAvailable;
    @Nonnull
    private String category;
    private String size;
}
