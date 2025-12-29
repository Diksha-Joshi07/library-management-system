package com.diksha.library.service;

import com.diksha.library.dto.response.TransactionResponseDTO;
import com.diksha.library.entity.Book;
import com.diksha.library.entity.Transaction;
import com.diksha.library.entity.User;
import com.diksha.library.enums.TransactionStatus;
import com.diksha.library.enums.UserRole;
import com.diksha.library.exception.BadRequestException;
import com.diksha.library.exception.ResourceNotFoundException;
import com.diksha.library.repository.BookRepository;
import com.diksha.library.repository.TransactionRepository;
import com.diksha.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private static final int MAX_BOOKS = 3;
    private static final int ISSUE_DAYS = 14;
    private static final double FINE_PER_DAY = 10;

    public TransactionService(TransactionRepository transactionRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String issueBook(String userEmail, String isbn) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getUserRole() != UserRole.ROLE_STUDENT) {
            throw new BadRequestException("Only students can issue books");
        }

        long issuedBooks = transactionRepository
                .countByUserAndStatus(user, TransactionStatus.ISSUED);

        if (issuedBooks >= MAX_BOOKS) {
            throw new BadRequestException("User already has 3 issued books");
        }

        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (!book.isAvailable()) {
            throw new ResourceNotFoundException("Book not available");
        }

        book.setAvailable(false);

        Transaction txn = Transaction.builder()
                .user(user)
                .book(book)
                .issuedDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(ISSUE_DAYS))
                .status(TransactionStatus.ISSUED)
                .fine(0.0)
                .build();

        transactionRepository.save(txn);
        bookRepository.save(book);

        return "Book issued successfully";
    }


    @Transactional
    public String returnBook(String isbn) {

        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Transaction txn = transactionRepository
                .findByBookAndStatus(book, TransactionStatus.ISSUED)
                .orElseThrow(() -> new ResourceNotFoundException("Active transaction not found"));

        LocalDate today = LocalDate.now();
        double fine = 0;

        if (today.isAfter(txn.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(txn.getDueDate(), today);
            fine = daysLate * FINE_PER_DAY;
        }

        txn.setReturnedDate(today);
        txn.setFine(fine);
        txn.setStatus(TransactionStatus.RETURNED);

        book.setAvailable(true);

        transactionRepository.save(txn);
        bookRepository.save(book);

        return "Book returned. Fine: ₹" + fine;
    }

    public List<TransactionResponseDTO> getTransactionsForUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return transactionRepository.findByUser(user)
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    public List<TransactionResponseDTO> getActiveIssuedBooks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return transactionRepository
                .findByUserAndStatus(user, TransactionStatus.ISSUED)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<TransactionResponseDTO> getAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<TransactionResponseDTO> getTransactionsByStatus(String status) {

        TransactionStatus txnStatus = TransactionStatus.valueOf(status);

        return transactionRepository.findByStatus(txnStatus)
                .stream()
                .map(this::mapToDto)
                .toList();
    }



    private TransactionResponseDTO mapToDto(Transaction txn) {
        return TransactionResponseDTO.builder()
                .userEmail(txn.getUser().getEmail())
                .bookTitle(txn.getBook().getTitle())
                .isbn(txn.getBook().getIsbn())
                .status(txn.getStatus().name())
                .issuedDate(txn.getIssuedDate())
                .dueDate(txn.getDueDate())
                .returnedDate(txn.getReturnedDate())
                .fine(txn.getFine())
                .build();
    }


}
