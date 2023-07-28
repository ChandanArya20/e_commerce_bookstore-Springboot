package in.ineuron.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ineuron.models.Book;
import in.ineuron.models.BookSeller;
import in.ineuron.models.ImageFile;
import in.ineuron.models.User;
import in.ineuron.repositories.BookRepositery;
import in.ineuron.repositories.ImageFileRepository;
import in.ineuron.repositories.SellerRepository;
import in.ineuron.repositories.UserRepository;

@Service
@Transactional
public class BookstoreServiceImpl implements BookstoreService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	SellerRepository sellerRepo;
	
	@Autowired
	BookRepositery bookRepo;
	
	@Autowired
	ImageFileRepository imageFileRepo;
	
	
	
	
	
	@Override
	public Boolean isUserAvailableByPhone(String phone) {
		
		return userRepo.existsByPhone(phone);
	}
	@Override
	public Boolean isUserAvailableByEmail(String email) {
		
		return userRepo.existsByEmail(email);
	}

	@Override
	public void registerUser(User user) {
		userRepo.save(user);
	}
	@Override
	public User fetchUserByPhone(String phone) {
		return userRepo.findByPhone(phone);

	}
	@Override
	public User fetchUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	
	
	@Override
	public Boolean isSellerAvailableByPhone(String phone) {
		return sellerRepo.existsByPhone(phone);
	}
	@Override
	public Boolean isSellerAvailableByEmail(String email) {
		return sellerRepo.existsByEmail(email);
	}
	@Override
	public Boolean isSellerAvailableBySellerId(String sellerId) {
		return sellerRepo.existsBySellerId(sellerId);
	}
	@Override
	public void registerSeller(BookSeller seller) {
		sellerRepo.save(seller);
		
	}
	@Override
	public BookSeller fetchSellerByPhone(String phone) {
		return sellerRepo.findByPhone(phone);
	}
	@Override
	public BookSeller fetchSellerByEmail(String email) {
		return sellerRepo.findByEmail(email);
	}
	@Override
	public BookSeller fetchSellerBySellerId(String sellerId) {
		return sellerRepo.findBySellerId(sellerId);
	}
	@Override
	public void insertBookInfo(Book book) {		
		Book savedResult = bookRepo.save(book);
	}
	@Override
	public List<Book> fetchBooksBySellerId(Long sellerId) {
		BookSeller seller = new BookSeller();
		seller.setId(sellerId);
		return bookRepo.findByBookSeller(seller);
	}
	@Override
	public ImageFile fetchBookImageById(Long id) {
		return imageFileRepo.findById(id).orElse(null);
	}
	@Override
	public Book fetchBookById(Long id) {
		return bookRepo.findById(id).orElse(null);
	}
	@Override
	public Book updateBook(Book book) {	
		return bookRepo.save(book);
	}
	@Override
	public Boolean checkBookStatus(Long id) {
		return bookRepo.findBookStatusById(id);
	}

	@Override
	public Integer activateBookStatus(Long id) {
		
		return bookRepo.activateBookStatusById(id);
	}
	@Override
	public Integer deactivateBookStatus(Long id) {
		
		return bookRepo.deactivateBookStatusById(id);
	}

	

	
	
	
	
	
	
	
	
	
	
	

}
