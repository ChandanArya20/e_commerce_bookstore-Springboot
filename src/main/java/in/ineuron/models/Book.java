package in.ineuron.models;

import javax.persistence.*;

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

//    @Column(nullable = false)
//    private String isbn;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private byte[] coverImage;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int publicationYear;

    @Column(nullable = false)
    private int numberOfPages;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String format;

    @Column(nullable = false)
    private int stockAvailability;
    
    @ManyToOne
    private BookSeller bookSeller;
     

//    @Column(nullable = false)
//    private double rating;
    
//    private String comments;
    
    
    
    
}

