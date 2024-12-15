package com.rovines.online.store.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Integer id;
    private String name;
    private Double price;
    private String image_url;
    private Integer quantity;
}
