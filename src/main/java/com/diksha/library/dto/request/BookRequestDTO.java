package com.diksha.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class BookRequestDTO {

    @NotBlank(message = "title cannot be empty")
    private String title;

    @NotBlank(message = "isbn cannot be empty")
    private String isbn;

    @NotBlank(message = "Please provide author name")
    private Long authorId;
}
