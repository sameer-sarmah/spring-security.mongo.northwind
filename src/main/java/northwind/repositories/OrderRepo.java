package northwind.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import northwind.documents.Order;

@Repository("mongoOrderRepo")
public interface OrderRepo extends MongoRepository<Order, String>{

}
