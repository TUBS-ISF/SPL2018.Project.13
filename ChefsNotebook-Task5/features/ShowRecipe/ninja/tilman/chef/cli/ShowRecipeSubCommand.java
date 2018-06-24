package ninja.tilman.chef.cli;

import java.util.List;

import ninja.tilman.chef.data.Recipe;

public class ShowRecipeSubCommand implements SubCommand {
	private RecipeSelector recipeSelector = new RecipeSelector();

	@Override
	public String getSubCommandIdentifier() {
		return "show";
	}

	@Override
	public String getDocumentation() {
		return "Show a recipe";
	}

	@Override
	public String execute(List<String> arguments) {
		String search = String.join(" ", arguments);
		Recipe recipe = recipeSelector.getRecipe(search);
		if (recipe == null) {
			return "No recipe found";
		} else {
			return recipe.getText();
		}
	}
}
