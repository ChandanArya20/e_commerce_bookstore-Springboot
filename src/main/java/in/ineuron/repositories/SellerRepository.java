package in.ineuron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ineuron.models.BookSeller;
import in.ineuron.models.User;

public interface SellerRepository extends JpaRepository<BookSeller, Long> {
	
	 public boolean existsByPhone(String phone);
	 public boolean existsByEmail(String email);
	 public boolean existsByUserId(String userId);
	 public User findByPhone(String phone);
	 public User findByEmail(String email);
	 public User findByUserId(String userid);

}
