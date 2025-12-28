package com.diksha.library.controller;

import com.diksha.library.dto.response.TransactionResponseDTO;
import com.diksha.library.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/issue")
    public ResponseEntity<String> issueBook(
            @RequestParam String email,
            @RequestParam String isbn) {

        return ResponseEntity.ok(
                transactionService.issueBook(email, isbn));
    }
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(
            @RequestParam String isbn) {

        return ResponseEntity.ok(
                transactionService.returnBook(isbn));
    }

    // STUDENT
    @GetMapping("/user")
    public ResponseEntity<List<TransactionResponseDTO>> getUserTransactions(
            @RequestParam String email) {

        return ResponseEntity.ok(
                transactionService.getTransactionsForUser(email));
    }

    @GetMapping("/user/active")
    public ResponseEntity<List<TransactionResponseDTO>> getActiveBooks(
            @RequestParam String email) {

        return ResponseEntity.ok(
                transactionService.getActiveIssuedBooks(email));
    }
    // ADMIN
    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        return ResponseEntity.ok(
                transactionService.getAllTransactions());
    }

    @GetMapping("/status")
    public ResponseEntity<List<TransactionResponseDTO>> getByStatus(
            @RequestParam String status) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByStatus(status));
    }
}
