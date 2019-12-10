package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

public interface RepositoryIngredient extends CrudRepository <Ingredient,String> {
}
