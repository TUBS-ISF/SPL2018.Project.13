package ninja.tilman.chef.cli;

import info.debatty.java.stringsimilarity.JaroWinkler;
import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.manager.RecipeManagerProvider;

public class NameRecipeSelector implements RecipeSelector {

	private final JaroWinkler jw = new JaroWinkler();

	@Override
	public Recipe getRecipe(String search) {
        search = search.toLowerCase();

        Double bestSimilarity = Double.NEGATIVE_INFINITY;
        Recipe bestMatch = null;
		for (Recipe recipe : RecipeManagerProvider.getManager().getRecipes()) {
			Double sim = jw.similarity(search, recipe.getName().toLowerCase());
			if (sim > bestSimilarity) {
				bestSimilarity = sim;
				bestMatch = recipe;
			}
		}
		
		return bestMatch;
	}

	@Override
	public boolean isFallback() {
		return true;
	}

}
