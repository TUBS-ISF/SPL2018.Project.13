package ninja.tilman.chef.manager;

public class RecipeManagerProvider {
	private static RecipeManager instance;
	
	public static synchronized RecipeManager getManager() {
		if (instance == null) {
			instance = createInstanceAccordingToProperty();
		}
		return instance;
	}
	
	private static RecipeManager createInstanceAccordingToProperty() {
		// #if MarkdownFiles
		return new MarkdownRecipeManager();
		// #endif
		// #if Database
//@		throw new UnsupportedOperationException("Database is not yet implemented");
		// #endif
	}
}
