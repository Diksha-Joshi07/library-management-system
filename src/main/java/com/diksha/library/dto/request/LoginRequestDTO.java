package com.diksha.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class LoginRequestDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}

