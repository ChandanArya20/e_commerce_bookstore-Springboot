package in.ineuron.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ineuron.models.BookSeller;
import in.ineuron.models.User;
import in.ineuron.repositories.SellerRepository;
import in.ineuron.repositories.UserRepository;

@Service
@Transactional
public class BookstoreServiceImpl implements BookstoreService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	SellerRepository sellerRepo;
	
	
	
	
	
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
	public Boolean isSellerAvailableByUserId(String userId) {
		return sellerRepo.existsByUserId(userId);
	}
	@Override
	public void registerSeller(BookSeller seller) {
		sellerRepo.save(seller);
		
	}
	@Override
	public User fetchSellerByPhone(String phone) {
		return sellerRepo.findByPhone(phone);
	}
	@Override
	public User fetchSellerByEmail(String email) {
		return sellerRepo.findByEmail(email);
	}
	@Override
	public User fetchSellerByUserId(String userId) {
		return sellerRepo.findByUserId(userId);
	}

	

	
	
	
	
	
	
	
	
	
	
	

}
