package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Taco;

public interface RepositoryTaco extends CrudRepository<Taco, Long> {
}
