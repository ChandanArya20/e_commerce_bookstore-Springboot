package in.ineuron.restcontrollers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ineuron.dto.LoginRequest;
import in.ineuron.dto.LoginResponse;
import in.ineuron.dto.RegisterRequest;
import in.ineuron.models.User;
import in.ineuron.services.BookstoreService;

@RestController
@RequestMapping("/api/user")
public class SellerLoginRegister {

	@Autowired
	BookstoreService service;
	
	Integer i;
	
	@PostMapping("/register") 
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest requestData){
			
		System.out.println(requestData);
		
		 if (service.isUserAvailableByPhone(requestData.getPhone()))			 
			 return ResponseEntity.badRequest().body("Phone No. already registerd with another account");
	
		 if (requestData.getEmail()!=null && service.isUserAvailableByEmail(requestData.getEmail())) {	
			 System.out.println(requestData.getEmail());
			 return ResponseEntity.badRequest().body("Email already registerd with another account");
			 
		 }else {
			 User user = new User();
			 BeanUtils.copyProperties(requestData, user);
			 service.registerUser(user);
			 LoginResponse response = new LoginResponse();
			 if(user.getPhone()!=null)
				 response.setToken((user.getPhone()+user.getName()).replace(" ",""));
			 else
				 response.setToken((user.getEmail()+user.getName()).replace(" ",""));
				 
			 response.setUser(user);
			
			 return ResponseEntity.ok(response);
		 }        
	}
		 
	
	@PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginData) {
		   
		if(loginData.getPhone()!=null) {
			
			User user = service.fetchUserByPhone(loginData.getPhone());
			
			if(user==null) {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found for this phone No.");
			}
			else if (!user.getPassword().equals(loginData.getPassword())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
				
			}else {
				LoginResponse response = new LoginResponse();
				response.setToken((user.getPhone()+user.getName()).replace(" ",""));
				response.setUser(user);
				
				return ResponseEntity.ok(response);
			}
			
		}else {
			
			User user = service.fetchUserByEmail(loginData.getEmail());
			
			if(user==null) {
	        	
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found for this email");
	        }
	        else if (!user.getPassword().equals(loginData.getPassword())) {
	        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
	        	
	        }else {
	        	LoginResponse response = new LoginResponse();
				response.setToken((user.getEmail()+user.getName()).replace(" ", ""));
				response.setUser(user);
				
				return ResponseEntity.ok(response);
	        }
		}

    }	
	
}
