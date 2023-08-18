package in.ineuron.models;

import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String description;
    
   
    private String isbn;

    @Lob
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private ImageFile coverImage;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDate publishingYear;

    private int pages;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String format;

    @Column(nullable = false)
    private int stockAvailability;
    
    @Column(nullable = false)
    private int deliveryTime;
    
    private String edition;
    
    @ManyToOne
    private BookSeller bookSeller;
     

//    @Column(nullable = false)
//    private double rating;
    
//    private String comments;
    
    @Column(nullable = false)
    private Boolean status=true;
       
}

