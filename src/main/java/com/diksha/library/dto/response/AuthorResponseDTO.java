package com.diksha.library.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AuthorResponseDTO {
    private String name;
    private String bio;
    private List<String> books;
}
