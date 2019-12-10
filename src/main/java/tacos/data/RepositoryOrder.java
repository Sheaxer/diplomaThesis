package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositoryOrder extends CrudRepository<Order,Long> {

    List<Order> findByZip(String zip);
    List<Order> readOrdersByZipAndCreatedAtBetween(String zip, LocalDateTime startDate,LocalDateTime endDate);
}
