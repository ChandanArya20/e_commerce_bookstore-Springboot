package in.ineuron.models;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false)
    private Date orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

//    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String status;
    
    @OneToOne
    private Book book;
    
    @ManyToOne
    private User user;
    
}

   
