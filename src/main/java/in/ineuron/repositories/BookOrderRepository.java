package in.ineuron.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ineuron.models.BookOrder;
import in.ineuron.models.User;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long>{
	
	List<BookOrder> findByUser(User user);

}
