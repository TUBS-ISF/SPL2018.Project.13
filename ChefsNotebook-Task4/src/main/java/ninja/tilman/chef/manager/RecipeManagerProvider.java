package ninja.tilman.chef.manager;

import loader.PluginLoader;

public class RecipeManagerProvider {	
	public static synchronized RecipeManager getManager() {
		return PluginLoader.load(RecipeManager.class).get(0);
	}
}
