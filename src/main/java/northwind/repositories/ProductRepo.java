package northwind.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import northwind.documents.Product;

@Repository("mongoProductRepo")
public interface ProductRepo extends MongoRepository<Product, String>{

}
