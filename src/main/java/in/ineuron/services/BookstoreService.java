package in.ineuron.services;

import java.util.List;

import in.ineuron.models.Book;
import in.ineuron.models.BookSeller;
import in.ineuron.models.ImageFile;
import in.ineuron.models.User;

public interface BookstoreService {

	public Boolean isUserAvailableByPhone(String phone);
	public Boolean isUserAvailableByEmail(String email);
	public void registerUser(User user);
	public User fetchUserByPhone(String phone);
	public User fetchUserByEmail(String email);
	
	public Boolean isSellerAvailableByPhone(String phone);
	public Boolean isSellerAvailableByEmail(String email);
	public Boolean isSellerAvailableBySellerId(String sellerId);
	public void registerSeller(BookSeller seller);
	public BookSeller fetchSellerByPhone(String phone);
	public BookSeller fetchSellerByEmail(String email);
	public BookSeller fetchSellerBySellerId(String sellerId);
	
	
	public void insertBookInfo(Book book);
	public List<Book> fetchBooksBySellerId(Long sellerId);
	public ImageFile fetchBookImageById(Long id);
	public Book fetchBookById(Long id);
	public Book updateBook(Book book);
	public Boolean checkBookStatus(Long id);
	public Integer activateBookStatus(Long id);
	public Integer deactivateBookStatus(Long id);

}
