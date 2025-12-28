package com.diksha.library.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class BookResponseDTO {
    private String title;
    private String isbn;
    private String authorName;
    private boolean available;
}
