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

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String name;
	
	@Column(unique = true)
	String phone;
	
	@Column(unique = true)
	String email;
	
	String password;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Address> address;
	
}





