package ninja.tilman.chef.data;

import com.google.common.collect.ImmutableList;


public class Recipe {
	private final ImmutableList<Ingredient> ingredients;

	Recipe(String name, String description, String instructions, ImmutableList<Ingredient> ingredients) {
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}
	
	public ImmutableList<Ingredient> getIngredients() {
		return ingredients;
	}
}