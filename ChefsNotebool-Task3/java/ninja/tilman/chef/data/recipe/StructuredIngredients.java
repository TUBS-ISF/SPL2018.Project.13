package ninja.tilman.chef.data.recipe;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

import ninja.tilman.chef.data.base.Feature;
import ninja.tilman.chef.data.ingredient.Ingredient;

public class StructuredIngredients implements Feature<Recipe> {
	private final ImmutableList<Ingredient> ingredients;

	public StructuredIngredients(Collection<Ingredient> ingredients) {
		this.ingredients = ImmutableList.copyOf(ingredients);
	}

	public ImmutableList<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public String toString() {
		return ingredients.toString();
	}
}
