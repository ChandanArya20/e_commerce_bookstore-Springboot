package in.ineuron.services;

import java.util.List;

import in.ineuron.dto.AddressRequest;
import in.ineuron.dto.UserResponse;
import in.ineuron.models.Address;
import in.ineuron.models.Book;
import in.ineuron.models.BookOrder;
import in.ineuron.models.BookSeller;
import in.ineuron.models.Cart;
import in.ineuron.models.ImageFile;
import in.ineuron.models.User;
import in.ineuron.returntype.AddressReturn;

public interface BookstoreService {

	public Boolean isUserAvailableByPhone(String phone);
	public Boolean isUserAvailableByEmail(String email);
	public void registerUser(User user);
	public User fetchUserByPhone(String phone);
	public User fetchUserByEmail(String email);
	public UserResponse fetchUserDetails(Long userId);
	public List<Address>  fetchAddressByUserId(Long userId);
	public Boolean insertUserAddress(AddressRequest address,  Long userId);
	
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
	
	public Boolean insertCartData(Cart cart);
	public List<Cart> getAllCartDataByUser(User user);
	public Boolean updateCartItemQuantity(Cart cart);
	public Boolean deleteCartItems(Cart[] carts);
	
	public Boolean insertOrder(List<BookOrder> orders);
	public List<BookOrder> fetchOrdersByUser(User user);
	public Boolean changeOrderStatus(Long orderId, String status);
	

}
















