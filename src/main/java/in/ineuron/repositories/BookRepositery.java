package in.ineuron.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.ineuron.models.Book;
import in.ineuron.models.BookSeller;

public interface BookRepositery extends JpaRepository<Book, Long> {
	
	 public List<Book> findByBookSeller(BookSeller seller);
	 
	 @Query("SELECT status FROM Book WHERE id=:id")
	 public Boolean findBookStatusById(Long id);
	 
	 @Modifying
	 @Query("UPDATE Book b SET b.status = true WHERE b.id = :id")
	 public Integer activateBookStatusById(Long id);
	 
	 @Modifying
	 @Query("UPDATE Book b SET b.status = false WHERE b.id = :id")
	 public Integer deactivateBookStatusById(Long id);
	 
	 
	 public List<Book> findByTitleContainingIgnoreCaseAndStatus(String query, Boolean status);
	 public List<Book> findByCategoryContainingIgnoreCaseAndStatus(String query, Boolean status);
	 public List<Book> findByDescriptionContainingIgnoreCaseAndStatus(String query, Boolean status);
	 


}
