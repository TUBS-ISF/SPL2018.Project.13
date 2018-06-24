package ninja.tilman.chef.data; 

public class Recipe {
	private final String ingredients;

	public Recipe(String name, String description, String instructions, String ingredients) {
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}
	
	public String getIngredients() {
		return ingredients;
	}
	
	public String getIngredientsText() {
		return ingredients;
	}
}