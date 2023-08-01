package in.ineuron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ineuron.models.BookOrder;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long>{

}
