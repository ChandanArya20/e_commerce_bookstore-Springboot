package in.ineuron.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
	
	
	
	
	// User Repository Operations---------------------------------------------------->
	
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

		Optional<User> userOptional = userRepo.findById(userId);
		if(userOptional.isPresent()) {
			
			User user = userOptional.get();
			UserResponse userResponse = new UserResponse();
			BeanUtils.copyProperties(user, userResponse);
			
			return userResponse;
		}
		
		return null;		
	}
	
	@Override
	public void insertUserAddress(AddressRequest address, Long userId) {
		
		System.out.println(address);
		Optional<User> userOptional = userRepo.findById(userId);
		
		if(userOptional.isPresent()) {
			
			User user = userOptional.get();
			List<Address> userAddress = user.getAddress();
			
			Address addressObj = new Address();
			BeanUtils.copyProperties(address, addressObj);
			userAddress.add(addressObj);
			
			userRepo.save(user);		
		}
	}
	
	@Override
	public List<Address> fetchAddressByUserId(Long userId) {
		
		User user = userRepo.findById(userId).orElse(null);
		List<Address> address=new ArrayList<>();
		
		if(user!=null)	{
			
			address=user.getAddress();			
		} 
		
		return address;
	}
	
	
	
	
	// Seller Repository Operations---------------------------------------------------->
	
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
	
	
	
	// Book Repository Operations---------------------------------------------------->
	
	@Override
	public void insertBookInfo(Book book) {	
		
		bookRepo.save(book);
	}
	
	@Override
	public List<Book> fetchBooksBySellerId(Long sellerId) {
		
		BookSeller seller = new BookSeller();
		seller.setId(sellerId);
		return bookRepo.findByBookSeller(seller);
	}
	
	@Override
	public Optional<ImageFile> fetchBookImageById(Long id) {
		
		return imageFileRepo.findById(id);
	}
	
	@Override
	public Optional<Book> fetchBookById(Long id) {
		
		return bookRepo.findById(id);
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
		
		if(tokens.length>1) {  		// length is not greater than one means there is no combination of words

			Set<String> stopWords=new HashSet<>(Arrays.asList("and","in","the","a","for"));
			
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
			
			Set<String> stopWords=new HashSet<>(Arrays.asList("and","in","the","a","for"));	
			
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

			Set<String> stopWords=new HashSet<>(Arrays.asList("and","in","the","a","for"));	
			
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
	public List<BookResponse> searchBooks(Integer perPage,Integer page,String query) {
		
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
		
		int startIndex = (page - 1) * perPage; 
		int endIndex = Math.min(startIndex + perPage, bookList.size()); // Ensure endIndex doesn't exceed the list size
	    
		List<BookResponse> pagedResults=new ArrayList<>();
		
		if(startIndex > endIndex) {
			
			 return pagedResults;		
		}else {
			
			// Create a sublist of bookList based on the startIndex and endIndex
			pagedResults = bookList.subList(startIndex, endIndex);
		}
			    
		return pagedResults;		
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
	
	@Override
	public List<BookResponse> getSuggestedBooksByTitle(String query, Integer size) {
		 
         PageRequest pageRequest = PageRequest.of(0, size); // Limit to the first 10 results
        
         List<Book> suggestedBooks = bookRepo.findByTitleContainingIgnoreCase(query, pageRequest);
        
        
         return bookUtils.getBookResponse(suggestedBooks);
        
	 }
	 
	 @Override
	 public List<String> getSuggestedBookNamesByTitle(String query, Integer size) {
		 
		 PageRequest pageRequest = PageRequest.of(0, size); // Limit to the first 10 results
		 
		 List<String> bookList = bookRepo.findBookNamesStartWith(query, pageRequest);			 
		 
//		 if(bookList.size()<size) {
//			 
//			 List<String> books = bookRepo.findBookNamesContains(query, pageRequest);
//			 
//			 Iterator<String> itr = books.iterator();
//			 int sizeCount=bookList.size();
//			 
//			 while(itr.hasNext() && sizeCount<10) {
//				 
//				 bookList.add(itr.next());
//				 sizeCount++;
//			 }
//		 }
		 
		 return bookList;
	 }

	 @Override
	 public List<String> getSuggestedBookNamesByExactMatch(String query, Integer size) {
		 
	     List<String> exactMatchBookNames = new ArrayList<>();
	     PageRequest pageRequest = PageRequest.of(0, 5*size);  //Fetchs large amount of data at a time
	     
	     while (exactMatchBookNames.size() < size) {
	         
	         List<String> bookNames = bookRepo.findBookNamesContains(query, pageRequest);
	         
	         // If database doesn't have more data
             if (bookNames.isEmpty()) {
                 break;
             }
	        
	         //Filter the exact matched book names
	         List<String> exactMatchedStrings = getExactMatchedContainingStrings(bookNames, query);
	         exactMatchBookNames.addAll(exactMatchedStrings);
	         	         	        
	         // Increment the page number to fetch the next set of data
	         pageRequest = PageRequest.of(pageRequest.getPageNumber() + 1, 5*size);
	     }
	     
	     // Slice the list to contain only the first 'size' elements before returning
	     if (exactMatchBookNames.size() > size) {
	         exactMatchBookNames = exactMatchBookNames.subList(0, size);
	     }
	     
	     return exactMatchBookNames;
	 }
	 
	 public List<String> getExactMatchedContainingStrings(List<String> textList, String key){
		 
		 List<String> exactMatchTextNames=new ArrayList<>();
		 
		 for (String text : textList) {
        	 
             // Replace all special characters with spaces
             String sanitizedTextName = text.replaceAll("[^a-zA-Z0-9\\s]", " ");
             
             String[] words = sanitizedTextName.split("\\s+");
             
			// Check if any word in the book name is an exact match to the query
             for (String word : words) {
            	 
                 if (word.equalsIgnoreCase(key)) {
                	 
                	 exactMatchTextNames.add(text);
                     break;
                 }
             }
         }
		 
		 return exactMatchTextNames;
	 }

	
	 
	 
	 
	// Cart Repository Operations---------------------------------------------------->
	 
	@Override
	public void insertCartData(Cart cart) {
		
		cartRepo.save(cart);		
	}
	
	@Override
	public List<Cart> getAllCartDataByUser(User user) {
		
		return cartRepo.findByUser(user);
	}
	
	@Override
	public void updateCartItemQuantity(Cart cart) {
		
	    Optional<Cart> existingCartItem = cartRepo.findById(cart.getId());

	    if (existingCartItem.isPresent()) {
	    	
	        Cart dbCartItem = existingCartItem.get();
	        
	        dbCartItem.setQuantity(cart.getQuantity());

	        // Save the updated cart item
	        cartRepo.save(dbCartItem);	      
	    } 	    
	}
	
	@Override
	public void deleteCartItems(Cart[] carts) {
		
		cartRepo.deleteAllInBatch(Arrays.asList(carts));	
	}
	
	 
	 
	
	// BookOrder Repository Operations---------------------------------------------------->
	
	@Override
	public Boolean insertOrder(List<BookOrder> orders) {
		
		List<BookOrder> savedOrders = orderRepo.saveAll(orders);
		
		return !savedOrders.isEmpty();
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
		
		return updateOrderStatus==1;
	}

	@Override
	public List<BookOrderResponse> fetchOrdersBySellerId(Long id) {
		
		List<Book> bookList = fetchBooksBySellerId(id);
		
		List<BookOrder> orderList = orderRepo.findByBook(bookList);
		
		List<BookOrderResponse> orders = bookUtils.getBookOrderResponse(orderList);
		
		return orders;
	}

	@Override
	public BookOrder getOrderById(Long orderId) {
		
		return orderRepo.findById(orderId).orElse(null);
	}
	
	

	

}





