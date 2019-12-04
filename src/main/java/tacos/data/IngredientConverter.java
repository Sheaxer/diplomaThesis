package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.Ingredient;

@Component
public class IngredientConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientConverter(IngredientRepository ingredientRepository)
    {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String s) {

        return ingredientRepository.findOne(s);

    }
}
