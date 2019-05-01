package northwind.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import northwind.documents.Shipper;

@Repository("mongoShipperRepo")
public interface ShipperRepo extends MongoRepository<Shipper, String>{

}
