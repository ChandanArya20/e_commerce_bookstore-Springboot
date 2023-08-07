package in.ineuron.dto;

import java.time.LocalDate;

import in.ineuron.models.Address;
import lombok.Data;

@Data
public class BookOrderResponse {
	
    private Long id;

    private LocalDate deliveryDate;

    private String status;
    
    private BookResponse book;
    
    private Integer quantity;
    
    private Address deliveryAddress;

}
