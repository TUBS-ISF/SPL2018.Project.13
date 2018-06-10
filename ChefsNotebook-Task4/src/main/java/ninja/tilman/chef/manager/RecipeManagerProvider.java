package ninja.tilman.chef.manager;

import loader.PluginLoader;

public class RecipeManagerProvider {
	private static RecipeManager instance;
	
	public static synchronized RecipeManager getManager() {
		if (instance == null) {
			instance = createInstanceAccordingToProperty();
		}
		return instance;
	}
	
	private static RecipeManager createInstanceAccordingToProperty() {
		return PluginLoader.load(RecipeManager.class).get(0);
	}
}
