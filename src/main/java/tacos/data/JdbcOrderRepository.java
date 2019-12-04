package tacos.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.Order;

import java.time.LocalDateTime;
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
       // this.jdbc = jdbc;
        this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {

        order.setCreatedAt(LocalDateTime.now());
        long id = saveOrderDetails(order);
        return order;
    }

    private long saveOrderDetails(Order order)
    {
        Map<String,Object> values = objectMapper.convertValue(order,Map.class);
        for(Map.Entry<String,Object> entry : values.entrySet())
        {

            log.info(entry.getKey() + " " + entry.getValue().toString());

            //Log.info(entry.getKey() + " " + entry.getValue().toString());
        }
        values.put("placedAt", order.getCreatedAt());
        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }




}
