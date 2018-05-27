package ninja.tilman.chef.manager;

import properties.PropertyManager;

public class RecipeManagerProvider {
	private static RecipeManager instance;
	
	public static synchronized RecipeManager getManager() {
		if (instance == null) {
			instance = createInstanceAccordingToProperty();
		}
		return instance;
	}
	
	private static RecipeManager createInstanceAccordingToProperty() {
		if (PropertyManager.getProperty("MarkdownFiles")) {
			return new MarkdownRecipeManager();
		} 
		if (PropertyManager.getProperty("Database")) {
			throw new UnsupportedOperationException("Database is not yet implemented");
		} 
		throw new IllegalStateException();
	}
}
