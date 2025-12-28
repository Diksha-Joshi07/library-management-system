package com.diksha.library.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class TransactionResponseDTO {
    private String userEmail;
    private String bookTitle;
    private String isbn;
    private String status;
    private LocalDate issuedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private Double fine;
}
