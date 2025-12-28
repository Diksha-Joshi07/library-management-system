package com.diksha.library.entity;

import com.diksha.library.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;   // STUDENT

    @ManyToOne
    private Book book;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDate issuedDate;

    private LocalDate dueDate;

    private LocalDate returnedDate;

    private Double fine;
}

