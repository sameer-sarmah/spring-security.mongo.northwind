package northwind.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import northwind.documents.Product;
import northwind.repositories.ProductRepo;
import northwind.services.AuthorizationService;


@RestController
public class NorthwindController {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@PreAuthorize("@authorizationService.canRead()")
	@ResponseBody
    @GetMapping("/api/products")
    public List<Product> findAllProducts(){
    	return productRepo.findAll();
    }

	@PreAuthorize("@authorizationService.canRead()")
	@ResponseBody
    @GetMapping("/api/product/{id}")
    public Product findProductById(@PathVariable("id") String id){
    	return productRepo.findById(id).get();
    }	
	
}
