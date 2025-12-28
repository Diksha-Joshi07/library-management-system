package com.diksha.library.repository;

import com.diksha.library.entity.Book;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorNameContainingIgnoreCase(String name);
    List<Book> findByAvailable(boolean available);
    Optional<Book> findByIsbn(String isbn);
}
