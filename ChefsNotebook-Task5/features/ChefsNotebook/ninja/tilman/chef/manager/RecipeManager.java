package ninja.tilman.chef.manager;

import java.util.List;

import ninja.tilman.chef.data.Recipe;

public class RecipeManager {
	private static RecipeManager instance;
	
	public static synchronized RecipeManager getManager() {
		if (instance == null) {
			instance = new RecipeManager();
		}
		return instance;
	}
	
	public List<Recipe> getRecipes() {
		throw new UnsupportedOperationException();
	}
}
