package ninja.tilman.chef.data.ingredients;

public class TextIngredientsClassProvider implements IngredientsClassProvider {

	@Override
	public Class<? extends Ingredients> getIngredientsClass() {
		return TextIngredients.class;
	}

}
