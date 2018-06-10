package ninja.tilman.chef.manager;

import java.util.List;

import loader.PluginLoader;
import ninja.tilman.chef.data.Recipe;

public interface RecipeManager {
	public static RecipeManager getManager() {
		return PluginLoader.load(RecipeManager.class).get(0);
	}
	
	public List<Recipe> getRecipes();


}
