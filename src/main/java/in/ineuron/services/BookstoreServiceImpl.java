package in.ineuron.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ineuron.models.Book;
import in.ineuron.models.BookOrder;
import in.ineuron.models.BookSeller;
import in.ineuron.models.Cart;
import in.ineuron.models.ImageFile;
import in.ineuron.models.User;
import in.ineuron.repositories.BookOrderRepository;
import in.ineuron.repositories.BookRepositery;
import in.ineuron.repositories.CartRepository;
import in.ineuron.repositories.ImageFileRepository;
import in.ineuron.repositories.SellerRepository;
import in.ineuron.repositories.UserRepository;

@Service
@Transactional
public class BookstoreServiceImpl implements BookstoreService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private SellerRepository sellerRepo;
	
	@Autowired
	private BookRepositery bookRepo;
	
	@Autowired
	private ImageFileRepository imageFileRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private BookOrderRepository orderRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	
	
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
	
	@Override
	public Boolean insertCartData(Cart cart) {
		
		Cart insertedCartItem = cartRepo.save(cart);
		if(insertedCartItem!=null)
			return true;
		else
			return false;
	}
	
	@Override
	public List<Cart> getAllCartDataByUser(User user) {
		
		return cartRepo.findByUser(user);
	}
	
	@Override
	public Boolean updateCartItemQuantity(Cart cart) {
	    // Check if the cart item exists in the database by fetching it using the ID
	    Optional<Cart> existingCartItem = cartRepo.findById(cart.getId());

	    if (existingCartItem.isPresent()) {
	        Cart dbCartItem = existingCartItem.get();
	        // Update the properties of the existing cart item with the new data
	        dbCartItem.setQuantity(cart.getQuantity());

	        // Save the updated cart item
	        Cart updatedCartItem = cartRepo.save(dbCartItem);

	        if (updatedCartItem != null) {
	            return true;
	        } else {
	            return false;
	        }
	    } else {
	        // Handle the case when the cart item with the provided ID does not exist
	        return false;
	    }
	}

	
	@Override
	public Boolean deleteCartItems(Cart[] carts) {
		
		entityManager.flush();
		cartRepo.deleteAllInBatch(Arrays.asList(carts));
		
		return true;
	}
	
	@Override
	public Boolean insertOrder(List<BookOrder> orders) {
		List<BookOrder> savedAll = orderRepo.saveAll(orders);
		if(savedAll!=null)
			return true;
		else
			return false;
	}

	

	
	
	
	
	
	
	
	
	
	
	

}
