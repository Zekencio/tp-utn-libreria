package com.example.demo.book.repository;

import com.example.demo.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
	// checks if any book references the given author id
	boolean existsByAuthor_Id(Long authorId);

	// checks if any book references the given genre id (in the many-to-many relationship)
	boolean existsByGenres_Id(Long genreId);
}
