package com.diksha.library.repository;

import com.diksha.library.entity.Book;
import com.diksha.library.entity.User;
import com.diksha.library.entity.Transaction;
import com.diksha.library.enums.TransactionStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    long countByUserAndStatus(User user, TransactionStatus status);

    Optional<Transaction> findByBookAndStatus(Book book, TransactionStatus status);

    List<Transaction> findByUser(User user);

    List<Transaction> findByUserAndStatus(User user, TransactionStatus status);

    List<Transaction> findByStatus(TransactionStatus status);

    List<Transaction> findByBookIsbn(String isbn);

    List<Transaction> findByUserEmail(String email);
}
