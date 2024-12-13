package com.online.store.server.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 70)
    private String username;
    @NotBlank
    @Email
    @Size(min = 3, max = 70)
    private String email;
    @NotBlank
    @Size(min = 3)
    private String password;
}
