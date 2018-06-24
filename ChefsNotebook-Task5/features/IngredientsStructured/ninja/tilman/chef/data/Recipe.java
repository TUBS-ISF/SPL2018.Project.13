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
	
	private String getIngredientsText() {
		StringBuilder b = new StringBuilder();
		buildIngredientsText(b, ingredients, 0);
		return b.toString();
	}
	
	private void buildIngredientsText(StringBuilder b, ImmutableList<Ingredient> ingredients, int depth) {
		for (Ingredient ingredient : ingredients) {
			for (int i = 0; i < depth; i++) {
				b.append('\t');
			}
			b.append("- ");
			b.append(ingredient.getName().trim());
			b.append("\n");
			buildIngredientsText(b, ingredient.getChildren(), depth + 1);
		}
	}
}