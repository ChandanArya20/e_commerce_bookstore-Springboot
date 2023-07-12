package in.ineuron.services;

import in.ineuron.models.User;

public interface BookstoreService {

	public Boolean isUserAvailableByPhone(String phone);
	public Boolean isUserAvailableByEmail(String email);
	public void registerUser(User user);
	public User fetchUserByPhone(String phone);
	public User fetchUserByEmail(String email);
//	public String authenticateUserByPhone(String phone, String password);
//	public String authenticateUserByEmail(String email, String password);
}
