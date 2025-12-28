package com.diksha.library.service;

import com.diksha.library.dto.request.AuthorRequestDTO;
import com.diksha.library.dto.response.AuthorResponseDTO;
import com.diksha.library.entity.Author;
import com.diksha.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        author.setBio(dto.getBio());
        authorRepository.save(author);
        return mapToDTO(author);
    }

    public AuthorResponseDTO getAuthorByName(String name) {
        Author author = authorRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return mapToDTO(author);
    }

    private AuthorResponseDTO mapToDTO(Author author) {
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setName(author.getName());
        dto.setBio(author.getBio());
        dto.setBooks(author.getBooks().stream().map(Book::getTitle).toList());
        return dto;
    }
}
