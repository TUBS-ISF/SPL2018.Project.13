package ninja.tilman.chef.manager;

import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.data.RecipeBuilder;

public class RecipeManager {
	private void setIngredients(RecipeBuilder builder, Section ingredientsSection) {
		builder.setIngredients(ingredientsSection.getText());
	}
}