package northwind.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import northwind.documents.Customer;

@Repository("mongoCustomerRepo")
public interface CustomerRepo extends MongoRepository<Customer, String>{

}
