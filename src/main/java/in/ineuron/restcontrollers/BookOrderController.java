package in.ineuron.restcontrollers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ineuron.dto.BookOrderRequest;
import in.ineuron.models.BookOrder;
import in.ineuron.services.BookstoreService;

@RestController
@RequestMapping("/api/order")
public class BookOrderController {

	@Autowired
	BookstoreService service;
	
	@PostMapping("placeOrder")
	public ResponseEntity<String> submitOrder( @RequestBody BookOrderRequest[] orderData){
		 
		List<BookOrder> bookList=new ArrayList<>();
		for(BookOrderRequest order:orderData) {
			
			BookOrder bookOrder = new BookOrder();
			BeanUtils.copyProperties(order, bookOrder);
			
			int deliveryTime=order.getBook().getDeliveryTime();
			LocalDate deliverydate=LocalDate.now().plusDays(deliveryTime);
			bookOrder.setDeliveryDate(deliverydate);
			
			bookList.add(bookOrder);
			
		}
			
		service.insertOrder(bookList);
		return  ResponseEntity.ok("Order submitted successfully...");
	}
}






