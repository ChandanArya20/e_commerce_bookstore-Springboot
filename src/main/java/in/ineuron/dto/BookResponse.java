package in.ineuron.dto;

import javax.persistence.Column;

import lombok.Data;

@Data
public class BookResponse {
	
	private Long id;
    
    private String title;

    private String author;
    
    private double price;
    
    private int stockAvailability;
    
    private String imageURL;
    
    private Boolean status;

	
}
