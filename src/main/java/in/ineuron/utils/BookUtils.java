package in.ineuron.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import in.ineuron.dto.BookOrderResponse;
import in.ineuron.dto.BookResponse;
import in.ineuron.models.Book;
import in.ineuron.models.BookOrder;

@Component
public class BookUtils {
	
	@Value("${baseURL}")
	private  String baseURL;
	
	
	public BookResponse getBookResponse(Book book){
		
		BookResponse bookResponse = new BookResponse();
		BeanUtils.copyProperties(book, bookResponse);
		bookResponse.setImageURL(baseURL+"/api/image/"+book.getCoverImage().getId());
				
		return bookResponse;
		
	}
	
	public List<BookResponse> getBookResponse(List<Book> books){
		
		List<BookResponse> bookResponses = new ArrayList<>(); 
		
		books.forEach(book->{
			
			BookResponse bookResponse = getBookResponse(book);
			
			bookResponses.add(bookResponse);
		});
		
		return bookResponses;
		
	}
	
	public BookResponse getBookResponse(BookOrder order){
		
		BookResponse bookResponse = new BookResponse();
		BeanUtils.copyProperties(order.getBook(), bookResponse);
		bookResponse.setImageURL(baseURL+"/api/image/"+order.getBook().getCoverImage().getId());
		
		return bookResponse;
		
	}
	
	public List<BookOrderResponse> getBookOrderResponse(List<BookOrder> orderList){
		
		List<BookOrderResponse> orders = new ArrayList<>(); 
		
			orderList.forEach(order->{
			
			BookOrderResponse orderResponse = new BookOrderResponse();
			BeanUtils.copyProperties(order, orderResponse);
			
			int year = order.getOrderDateTime().getYear();
			int month = order.getOrderDateTime().getMonthValue();
			int day = order.getOrderDateTime().getDayOfMonth();
			
			LocalDate orderDate= LocalDate.of(year, month, day);
			orderResponse.setOrderDate(orderDate);
			
			
			BookResponse bookResponse = new BookResponse();
			BeanUtils.copyProperties(order.getBook(), bookResponse);
			bookResponse.setImageURL(baseURL+"/api/image/"+order.getBook().getCoverImage().getId());
			
			orderResponse.setBook(bookResponse);
			orders.add(orderResponse);
		});	
		
		return orders;
		
	}
	
	
	
	
}
