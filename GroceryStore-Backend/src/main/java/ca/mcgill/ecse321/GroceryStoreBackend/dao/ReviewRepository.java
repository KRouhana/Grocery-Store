package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
  
  List<Review> findByCustomer(Customer customerEmail);
  
  boolean existsByCustomerAndOrder(Customer customerEmail, Order orderId);

  Review findReviewByCustomerAndOrder(Customer customerEmail, Order orderId);
  
  Review findReviewById(Long reviewId);

}
