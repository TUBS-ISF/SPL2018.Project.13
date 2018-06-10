package ninja.tilman.chef.data;

import com.google.common.base.MoreObjects;

import ninja.tilman.chef.data.ingredients.Ingredients;

public class Recipe {
	
	private final String name;
	private final String description;
	private final String instructions;
	private final Ingredients ingredients;
	
	public Recipe(String name, String description, String instructions, Ingredients ingredients) {
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public String getInstructions() {
		return instructions;
	}
	public Ingredients getIngredients() {
		return ingredients;
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("description", description)
				.add("instructions", instructions)
				.add("ingredients", ingredients)
				.toString();
	}
}
