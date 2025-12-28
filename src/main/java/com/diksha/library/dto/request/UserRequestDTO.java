package com.diksha.library.dto.request;

import com.diksha.library.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class UserRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "user email must not be blank")
    private String email;

    private String phone;

    private String password;

    @NotBlank(message = "Either STUDENT or ADMIN")
    private UserRole userRole;
}
