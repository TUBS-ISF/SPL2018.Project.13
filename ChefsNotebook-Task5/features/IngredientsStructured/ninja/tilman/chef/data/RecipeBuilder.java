package ninja.tilman.chef.data;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

public class RecipeBuilder {
	private ImmutableList<Ingredient> ingredients;

	public RecipeBuilder setIngredients(Collection<Ingredient> ingredients) {
		this.ingredients = ImmutableList.copyOf(ingredients);
		return this;
	}
	
	public Recipe build() {
		return new Recipe(name, description, instructions, ingredients);
	}
}
