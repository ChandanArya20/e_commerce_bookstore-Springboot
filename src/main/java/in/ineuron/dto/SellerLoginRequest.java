package in.ineuron.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SellerLoginRequest {

	@NotBlank(message = "UserId should not be empty")
    @Pattern(regexp = "^(?!.*\\s).*$",
             message = "Space is not allowed between")
	String userId;
	
	@NotBlank(message = "Password should not be empty")
	@Pattern(regexp = "^(?!.*\\s).*$",
	message = "Space is not allowed between")
	String password;

	
	public void setUserName(String userId) {
		this.userId = userId.trim();
	}
		
	public void setPassword(String password) {
		this.password = password.trim();
	}
	
}
