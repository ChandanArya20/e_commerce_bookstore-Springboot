package in.ineuron.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ineuron.models.User;
import in.ineuron.repositories.UserRepository;

@Service
@Transactional
public class BookstoreServiceImpl implements BookstoreService {

	@Autowired
	UserRepository userRepo;
	
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

	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public String authenticateUserByPhone(String phone, String password) {
//		
//		User registeredData = userRepo.findByPhone(phone);
//		if (registeredData == null)
//			return "notFound";
//				
//		if(registeredData.getPassword().equals(password))		
//	        return "success";
//	    else {
//	        return "wrongPassword";
//	    }
//	}
//	@Override
//	public String authenticateUserByEmail(String email, String password) {
//		
//		User registeredData = userRepo.findByEmail(email);
//		if (registeredData == null)
//			return "notFound";
//		
//		if(registeredData.getPassword().equals(password))		
//			return "success";
//		else {
//			return "wrongPassword";
//		}
//	}

}
