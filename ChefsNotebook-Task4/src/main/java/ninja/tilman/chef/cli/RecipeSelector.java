package ninja.tilman.chef.cli;

import ninja.tilman.chef.data.Recipe;

public interface RecipeSelector {
	public Recipe getRecipe(String selector);
	public boolean isFallback();
}
