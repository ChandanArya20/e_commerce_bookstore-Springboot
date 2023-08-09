package in.ineuron.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import in.ineuron.dto.AddressRequest;
import in.ineuron.dto.UserResponse;
import in.ineuron.models.Address;
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
import in.ineuron.returntype.AddressReturn;

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
	public UserResponse fetchUserDetails(Long userId) {

		User user = userRepo.findById(userId).orElse(null);
		if(user!=null) {
			UserResponse userResponse = new UserResponse();
			BeanUtils.copyProperties(user, userResponse);
			return userResponse;
		}
		return null;		
	}
	
	@Override
	public Boolean insertUserAddress(AddressRequest address, Long userId) {
		
		System.out.println(address);
		User user = userRepo.findById(userId).orElse(null);
		if(user!=null) {
			
			List<Address> userAddress = user.getAddress();
			
			Address addressObj = new Address();
			BeanUtils.copyProperties(address, addressObj);
			System.out.println(addressObj);
			userAddress.add(addressObj);
			userRepo.save(user);
			entityManager.flush();
			return true;
			
		} else {
			return false;
		}
	}
	
	@Override
	public List<Address> fetchAddressByUserId(Long userId) {
		
		User user = userRepo.findById(userId).orElse(null);
		List<Address> address=new ArrayList<>();
		
		if(user!=null)	{
			address=user.getAddress();		
			return address;
			
		} else {
			return null;
		}
			
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
		
	    Optional<Cart> existingCartItem = cartRepo.findById(cart.getId());

	    if (existingCartItem.isPresent()) {
	        Cart dbCartItem = existingCartItem.get();
	        
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
		
		for(Cart cart: carts){	
			
			cartRepo.delete(cart);
		}
		
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
	@Override
	public List<BookOrder> fetchOrdersByUser(User user) {
		
		return orderRepo.findByUser(user);
	}

	@Override
	public Boolean changeOrderStatus(Long orderId, String status) {
		
		Integer updateOrderStatus = orderRepo.updateOrderStatus(orderId, status);
		
		if(updateOrderStatus==1)
			return true;
		else		
			return false;
	}

	
	

}
