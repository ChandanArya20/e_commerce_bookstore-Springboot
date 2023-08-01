package in.ineuron.dto;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import in.ineuron.models.BookSeller;
import in.ineuron.models.ImageFile;
import lombok.Data;

@Data
public class AllDataBookResponse {

	    private Long id;

	    private String title;
	    
	    private String author;

	    private String description;
	      
	    private String isbn;

	    private String imageURL;

	    private double price;

	    private String language;

	    private String category;

	    private LocalDate publishingYear;

	    private int pages;

	    private String publisher;

	    private String format;

	    private int stockAvailability;
	    
	    private int deliveryTime;
	    
	    private int edition;
	    
//	    private BookSeller bookSeller;
	     

//	    @Column(nullable = false)
//	    private double rating;
	    
//	    private String comments;
	    
	    private Boolean status;
}
