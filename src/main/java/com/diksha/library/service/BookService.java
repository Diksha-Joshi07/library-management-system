package com.diksha.library.service;

import com.diksha.library.dto.request.BookRequestDTO;
import com.diksha.library.dto.response.BookResponseDTO;
import com.diksha.library.entity.Author;
import com.diksha.library.entity.Book;
import com.diksha.library.exception.ResourceNotFoundException;
import com.diksha.library.repository.AuthorRepository;
import com.diksha.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public BookResponseDTO createBook(BookRequestDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setAuthor(author);
        bookRepository.save(book);

        return mapToDTO(book);
    }

    public List<BookResponseDTO> searchBooks(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream().map(this::mapToDTO).toList();
    }

    private BookResponseDTO mapToDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setAuthorName(book.getAuthor().getName());
        dto.setAvailable(book.isAvailable());
        return dto;
    }
}

