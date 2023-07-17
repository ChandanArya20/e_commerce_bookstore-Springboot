package in.ineuron.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false)
	String name;
	
	@Column(unique = true, nullable = false)
	String phone;
	
	@Column(unique = true)
	String email;
	
	@Column(nullable = false)
	String password;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Address> address;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<BookOrder> orders;
	
	

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public List<Address> getAddress() {
		return address;
	}
	
	public List<BookOrder> getOrders() {
		return orders;
	}
		
	@JsonIgnore
	public String getPassword() {
		return password;
	}

		
}





