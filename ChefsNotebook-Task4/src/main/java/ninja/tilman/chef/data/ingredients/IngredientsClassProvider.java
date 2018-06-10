package ninja.tilman.chef.data.ingredients;

import loader.PluginLoader;

public interface IngredientsClassProvider {
	public static IngredientsClassProvider getInstance() {
		return PluginLoader.load(IngredientsClassProvider.class).get(0);
	}
	
	public Class<? extends Ingredients> getIngredientsClass();
}
