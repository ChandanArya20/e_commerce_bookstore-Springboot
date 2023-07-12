package in.ineuron.repositories;

import org.springframework.data.repository.CrudRepository;

import in.ineuron.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

     public boolean existsByPhone(String phone);
	 public boolean existsByEmail(String email);
	 public User findByPhone(String phone);
	 public User findByEmail(String email);
	 
}
