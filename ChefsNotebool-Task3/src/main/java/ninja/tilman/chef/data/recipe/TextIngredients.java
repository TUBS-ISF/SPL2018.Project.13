package ninja.tilman.chef.data.recipe;

import ninja.tilman.chef.data.base.Feature;

public class TextIngredients implements Feature<Recipe> {
	private final String ingredients;

	public TextIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getIngredients() {
		return ingredients;
	}
	
	public String toString() {
		return ingredients;
	}
}
