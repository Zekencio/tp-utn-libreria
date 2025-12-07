package com.example.demo.book.repository;

import com.example.demo.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< Updated upstream
public interface BookRepository extends JpaRepository<Book,Long> {
=======
public interface BookRepository extends JpaRepository<Book,Long>,JpaSpecificationExecutor<Book> {
	// checks if any book references the given author id
>>>>>>> Stashed changes
	boolean existsByAuthor_Id(Long authorId);

	boolean existsByGenres_Id(Long genreId);

	boolean existsBySeller_Id(Long sellerId);

	java.util.List<Book> findBySeller_Id(Long sellerId);
}
