package in.ineuron.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import in.ineuron.dto.AddressRequest;
import in.ineuron.dto.BookOrderResponse;
import in.ineuron.dto.BookResponse;
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

import in.ineuron.utils.*;

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
	
	@Value("${baseURL}")
	private String baseURL;
	
	@Autowired
	BookUtils bookUtils;
	
	
	
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
	
	        return false;
	    }
	}

	
	@Override
	public Boolean deleteCartItems(Cart[] carts) {
		
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
	@Override
	public List<BookOrderResponse> fetchOrdersByUser(User user) {
		
		List<BookOrder> orderList = orderRepo.findByUser(user);
		
		List<BookOrderResponse> orders = bookUtils.getBookOrderResponse(orderList);
	
		return orders;
	}

	@Override
	public Boolean changeOrderStatus(Long orderId, String status) {
		
		Integer updateOrderStatus = orderRepo.updateOrderStatus(orderId, status);
		
		if(updateOrderStatus==1)
			return true;
		else		
			return false;
	}

	@Override
	public List<BookOrderResponse> fetchOrdersBySellerId(Long id) {
		
		List<Book> bookList = fetchBooksBySellerId(id);
		
		List<BookOrder> orderList = orderRepo.findByBook(bookList);
		
		List<BookOrderResponse> orders = bookUtils.getBookOrderResponse(orderList);
		
		return orders;
	}

	@Override
	public List<BookResponse> searchBooksByTitle(String query) {
		
		List<Book> bookList=new ArrayList<>();
		Set<Long> uniqueBookIds = new HashSet<>();
		
		query = query.trim();
		
		List<Book> books = bookRepo.findByTitleContainingIgnoreCaseAndStatus(query, true);
		for(Book book: books) {
			
			if(uniqueBookIds.add(book.getId()))
				bookList.add(book);
		}	
		
		String[] tokens = query.toLowerCase().split("\\s+");
		
		if(tokens.length>1) {

			Set<String> stopWords=new HashSet<String>(Arrays.asList("and","in","the","a","for"));
			System.out.println(stopWords);
			
			String[] searchQueryList = Arrays
										   .stream(tokens)
										   .filter(token->!stopWords.contains(token))
										   .toArray(String[]::new);
			
			for(String singleQuery: searchQueryList) {
				
				List<Book> allBook = bookRepo.findByTitleContainingIgnoreCaseAndStatus(singleQuery, true);		
				
				for(Book book: allBook) {
					
					if(uniqueBookIds.add(book.getId()))
						bookList.add(book);
				}
			}
			
		}
			
		List<BookResponse> bookResponse = bookUtils.getBookResponse(bookList);
		
		return bookResponse;
	}

	@Override
	public List<BookResponse> searchBooksByCategory(String query) {
		
		List<Book> bookList=new ArrayList<>();
		Set<Long> uniqueBookIds = new HashSet<>();
		
		query = query.trim();
		
		List<Book> books = bookRepo.findByCategoryContainingIgnoreCaseAndStatus(query, true);
		for(Book book: books) {
			
			if(uniqueBookIds.add(book.getId()))
				bookList.add(book);
		}	
		
		String[] tokens = query.toLowerCase().split("\\s+");
		System.out.println(Arrays.toString(tokens)+"Line-396");
		
		
		if(tokens.length>1) {
			
			Set<String> stopWords=new HashSet<String>(Arrays.asList("and","in","the","a","for"));	
			
			String[] searchQueryList = Arrays
									   .stream(tokens)
									   .filter(token->!stopWords.contains(token))
									   .toArray(String[]::new);
			
			for(String singleQuery: searchQueryList) {

				List<Book> allBook = bookRepo.findByCategoryContainingIgnoreCaseAndStatus(singleQuery, true);		
				
				for(Book book: allBook) {				
					if(uniqueBookIds.add(book.getId()))
						bookList.add(book);
				}
			}
			
		}
			
		List<BookResponse> bookResponse = bookUtils.getBookResponse(bookList);
		
		return bookResponse;
	}

	@Override
	public List<BookResponse> searchBooksByDescription(String query) {
		
		List<Book> bookList=new ArrayList<>();
		Set<Long> uniqueBookIds = new HashSet<>();
		
		query = query.trim();
		
		List<Book> books = bookRepo.findByDescriptionContainingIgnoreCaseAndStatus(query, true);
		for(Book book: books) {
			
			if(uniqueBookIds.add(book.getId()))
				bookList.add(book);
		}	
		
		String[] tokens = query.toLowerCase().split("\\s+");
		
		if(tokens.length>1) {

			Set<String> stopWords=new HashSet<String>(Arrays.asList("and","in","the","a","for"));	
			
			String[] searchQueryList = Arrays
									   .stream(tokens)
									   .filter(token->!stopWords.contains(token))
									   .toArray(String[]::new);
			
			for(String singleQuery: searchQueryList) {
				
				List<Book> allBook = bookRepo.findByDescriptionContainingIgnoreCaseAndStatus(singleQuery, true);		
				
				for(Book book: allBook) {
					
					if(uniqueBookIds.add(book.getId()))
						bookList.add(book);
				}
			}
			
		}
			
		List<BookResponse> bookResponse = bookUtils.getBookResponse(bookList);
		
		return bookResponse;
	}

	@Override
	public List<BookResponse> searchBooks(String query) {
		
		ArrayList<BookResponse> bookList = new ArrayList<>();	
		List<BookResponse> searchedBooks;
		Set<Long> uniqueBookIds = new HashSet<>();
		
		searchedBooks = searchBooksByTitle(query);
		bookList.addAll(searchedBooks);
		
		for(BookResponse book:searchedBooks) {		
			uniqueBookIds.add(book.getId());
		}
		
		searchedBooks = searchBooksByCategory(query);
		for(BookResponse book : searchedBooks) {
			
			if(uniqueBookIds.add(book.getId())) {
				bookList.add(book);
			}
		}
		
		searchedBooks = searchBooksByDescription(query);
		for(BookResponse book : searchedBooks) {
			
			if(uniqueBookIds.add(book.getId())) {
				bookList.add(book);
			}
		}
		
		return bookList;
		
	}

	@Override
	public BookOrder getOrderById(Long OrderId) {
		
		return orderRepo.findById(OrderId).orElse(null);

	}
	
	@Override
	public Boolean increaseBookStock(Long bookId, Integer stockValue) {
		 
		Optional<Book> bookOptional = bookRepo.findById(bookId);
		if(bookOptional.isPresent()) {
			
			Book book = bookOptional.get();
			book.setStockAvailability(book.getStockAvailability()+stockValue);
			bookRepo.save(book);
		}
		return null;
	}
	
	@Override
	public Boolean decreaseBookStock(Long bookId, Integer stockValue) {
		Optional<Book> bookOptional = bookRepo.findById(bookId);
		if(bookOptional.isPresent()) {
			
			Book book = bookOptional.get();
			book.setStockAvailability(book.getStockAvailability()-stockValue);
			bookRepo.save(book);
		}
		return null;
	}

	

}





