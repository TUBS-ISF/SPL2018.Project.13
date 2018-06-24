package ninja.tilman.chef.data;

public class RecipeBuilder {
	private String ingredients;

	public RecipeBuilder setIngredients(String ingredients) {
		this.ingredients = ingredients;
		return this;
	}
	
	public Recipe build() {
		return new Recipe(name, description, instructions, ingredients);
	}
}
