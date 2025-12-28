package com.diksha.library.repository;

import com.diksha.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
    List<Author> findByNameContainingIgnoreCase(String name);
}
