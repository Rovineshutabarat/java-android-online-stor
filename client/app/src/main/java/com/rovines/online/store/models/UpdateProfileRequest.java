package com.rovines.online.store.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String contact;
    private String address;
}
