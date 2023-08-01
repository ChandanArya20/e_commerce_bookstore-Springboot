package in.ineuron.restcontrollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.PostRemove;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.ineuron.dto.BookAddRequest;
import in.ineuron.dto.BookResponse;
import in.ineuron.dto.LoginRequest;
import in.ineuron.dto.LoginResponse;
import in.ineuron.dto.SellerLoginRequest;
import in.ineuron.dto.SellerLoginResponse;
import in.ineuron.dto.SellerRegisterRequest;
import in.ineuron.models.Book;
import in.ineuron.models.BookSeller;
import in.ineuron.models.ImageFile;
import in.ineuron.models.User;
import in.ineuron.services.BookstoreService;
import lombok.val;

@RestController
@RequestMapping("/api/seller/book")
public class SellerServiceController {

	@Autowired
	BookstoreService service;
	
	
		
	
}

















