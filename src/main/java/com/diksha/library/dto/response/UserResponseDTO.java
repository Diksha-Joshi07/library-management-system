package com.diksha.library.dto.response;

import com.diksha.library.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class UserResponseDTO {
    private String name;
    private String email;
    private String phone;
    private UserRole userRole;
}
