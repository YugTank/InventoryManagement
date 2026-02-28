package com.inventory.inventory_management.dto.request;

import com.inventory.inventory_management.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Username required")
    @Size(min=3, max = 50, message = "Username size must be between 3 and 50")
    private String username;

    @NotBlank(message = "Password required")
    @Size(min=6,message = "Password must be at least 6 character long")
    private String password;

    @NotBlank(message = "Email required")
    @Size(max = 100, message = "Email size must be less than 100")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Role is required")
    private Role role;
}
