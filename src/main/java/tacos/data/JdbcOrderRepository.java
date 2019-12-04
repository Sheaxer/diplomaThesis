package tacos.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.Order;
import tacos.Taco;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;
    //private JdbcTemplate jdbc;

    public JdbcOrderRepository(JdbcTemplate jdbc)
    {
        log.info("created");
       // this.jdbc = jdbc;
        this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {

        order.setCreatedAt(LocalDateTime.now());
        long id = saveOrderDetails(order);
        for(Taco taco: order.getTacos())
        {
            saveTacoToOrder(taco,id);
        }

        return order;
    }

    private long saveOrderDetails(Order order)
    {
        log.info(order.toString());
        Map<String,Object> values = objectMapper.convertValue(order,Map.class);
        values.put("deliveryName",values.get("name"));
        values.put("deliveryStreet", values.get("street"));
        values.put("deliveryCity", values.get("city"));
        values.put("deliveryState",values.get("state"));
        values.put("deliveryZip",values.get("zip"));

        values.put("placedAt", order.getCreatedAt());
        log.info(values.toString());
        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId)
    {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
    }




}
