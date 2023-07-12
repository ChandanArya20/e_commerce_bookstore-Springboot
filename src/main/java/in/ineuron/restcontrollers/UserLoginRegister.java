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
import in.ineuron.dto.RegisterRequest;
import in.ineuron.models.User;
import in.ineuron.services.BookstoreService;

@RestController
@RequestMapping("/api/user")
public class UserLoginRegister {

	@Autowired
	BookstoreService service;
	
	
	@PostMapping("/register") 
	public ResponseEntity<String> registerUser(@RequestBody RegisterRequest requestData){
		
		 if (service.isUserAvailableByPhone(requestData.getPhone()))			 
			 return ResponseEntity.badRequest().body("Phone already registerd with another account");
	
		 if (requestData.getEmail()!=null && service.isUserAvailableByEmail(requestData.getEmail())) {			 
			 return ResponseEntity.badRequest().body("Email already registerd with another account");
			 
		 }else {
			 User user = new User();
			 BeanUtils.copyProperties(requestData, user);
			 service.registerUser(user);
			 return ResponseEntity.ok("User registered successfully");
		 }        
	}
		 
	
	@GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginData) {
		   
		System.out.println(loginData);
		
		if(loginData.getPhone()!=null) {
			
			User user = service.fetchUserByPhone(loginData.getPhone());
			
			if(user==null) {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found for this phone No.");
			}
			else if (!user.getPassword().equals(loginData.getPassword())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
				
			}else {
				return ResponseEntity.ok(user);
			}
			
		}else {
			
			User user = service.fetchUserByEmail(loginData.getEmail());
			
			if(user==null) {
	        	
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found for this email");
	        }
	        else if (!user.getPassword().equals(loginData.getPassword())) {
	        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
	        	
	        }else {
	        	return ResponseEntity.ok(user);
	        }
		}

    }	
	
}
