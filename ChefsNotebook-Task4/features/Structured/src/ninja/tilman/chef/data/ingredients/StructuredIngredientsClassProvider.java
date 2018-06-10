package ninja.tilman.chef.data.ingredients;

public class StructuredIngredientsClassProvider implements IngredientsClassProvider {

	@Override
	public Class<? extends Ingredients> getIngredientsClass() {
		return StructuredIngredients.class;
	}

}
