package com.online.store.server.payload.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    private String description;
    @NotNull
    @Positive
    private Double price;
    private String image_url;
    private Integer category_id;
}
