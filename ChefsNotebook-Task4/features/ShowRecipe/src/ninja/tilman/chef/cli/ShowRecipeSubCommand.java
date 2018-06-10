package ninja.tilman.chef.cli;

import java.util.List;

import loader.PluginLoader;
import ninja.tilman.chef.data.Recipe;

public class ShowRecipeSubCommand implements SubCommand {

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
		Recipe recipe = getRecipe(search);
		if (recipe == null) {
			return "No recipe found";
		} else {
			return recipe.getName();
		}
	}

	private Recipe getRecipe(String search) {
        
		for (RecipeSelector selector : PluginLoader.load(RecipeSelector.class)) {
			if (!selector.isFallback()) {
				Recipe result = selector.getRecipe(search);
				if (result != null) {
					return result;
				}
			}
		}
		
		for (RecipeSelector selector : PluginLoader.load(RecipeSelector.class)) {
			if (selector.isFallback()) {
				Recipe result = selector.getRecipe(search);
				if (result != null) {
					return result;
				}
			}
		}
		
		return null;
	}
}
