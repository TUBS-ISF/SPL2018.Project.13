package ninja.tilman.chef.cli;

import java.util.List;

import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.manager.RecipeManager;

public class ListRecipesSubCommand implements SubCommand {

	@Override
	public String getSubCommandIdentifier() {
		return "list";
	}

	@Override
	public String getDocumentation() {
		return "List all recipes";
	}

	@Override
	public String execute(List<String> arguments) {
		StringBuilder result = new StringBuilder();
		for (Recipe recipe :  RecipeManager.getManager().getRecipes()) {
			result.append(recipe.getName());
			result.append("\n");
		}
		return result.toString();
	}

}
