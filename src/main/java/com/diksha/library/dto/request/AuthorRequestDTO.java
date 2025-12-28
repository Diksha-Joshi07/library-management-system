package com.diksha.library.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AuthorRequestDTO {
    private String name;
    private String bio;
}
