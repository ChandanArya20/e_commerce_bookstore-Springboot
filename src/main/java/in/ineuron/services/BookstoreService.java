package in.ineuron.services;

import in.ineuron.models.BookSeller;
import in.ineuron.models.User;

public interface BookstoreService {

	public Boolean isUserAvailableByPhone(String phone);
	public Boolean isUserAvailableByEmail(String email);
	public void registerUser(User user);
	public User fetchUserByPhone(String phone);
	public User fetchUserByEmail(String email);
	
	public Boolean isSellerAvailableByPhone(String phone);
	public Boolean isSellerAvailableByEmail(String email);
	public Boolean isSellerAvailableByUserId(String userId);
	public void registerSeller(BookSeller seller);
	public User fetchSellerByPhone(String phone);
	public User fetchSellerByEmail(String email);
	public User fetchSellerByUserId(String userId);

}
