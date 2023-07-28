package in.ineuron.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ineuron.models.ImageFile;
import in.ineuron.services.BookstoreService;

@RestController
@RequestMapping("/api/image")
public class ImageFileController {
	
	@Autowired
	private BookstoreService service;
	
	@Value("${baseURL}")
	private String baseURL;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getBookImageById(@PathVariable Long id){
		
		ImageFile imageFile = service.fetchBookImageById(id);
		if(imageFile!=null) {
			
			return ResponseEntity.ok()
					.contentType(MediaType.valueOf(imageFile.getType()))
					.body(imageFile.getImageData());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found for this image id");
		}		
		
	}
	

}



	