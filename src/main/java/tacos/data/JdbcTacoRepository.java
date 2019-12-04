package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;
    public JdbcTacoRepository(JdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco design) {
        long tacoId = saveTacoInfo(design);
        design.setId(tacoId);
        for(Ingredient ingredient : design.getIngredients())
        {
            saveIngredientTaco(ingredient,tacoId);
        }
        return design;
    }

    private Long saveTacoInfo(Taco taco)
    {
        taco.setCreatedAt(LocalDateTime.now());
        PreparedStatementCreatorFactory factory =
                new PreparedStatementCreatorFactory("insert  into taco (name, createdAt) VALUES (?,?)",
                        Types.VARCHAR,Types.TIMESTAMP);
        factory.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = factory.
                newPreparedStatementCreator(Arrays.asList(taco.getName(),
                        Timestamp.valueOf(taco.getCreatedAt()))) ;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc,keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientTaco(Ingredient ingredient, long tacoId)
    {
        jdbc.update("insert into taco_ingredients (taco, ingredient) VALUES (?,?)", tacoId,ingredient.getId());
    }
}
