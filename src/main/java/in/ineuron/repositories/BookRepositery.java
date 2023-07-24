package in.ineuron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ineuron.models.Book;
import in.ineuron.models.BookSeller;

public interface BookRepositery extends JpaRepository<Book, Long> {
	 public Book findByBookSeller(BookSeller seller);

}
