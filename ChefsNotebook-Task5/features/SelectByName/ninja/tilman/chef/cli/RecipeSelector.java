package ninja.tilman.chef.cli;

import info.debatty.java.stringsimilarity.JaroWinkler;
import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.manager.RecipeManager;

public class RecipeSelector {

	private final JaroWinkler jw = new JaroWinkler();

	public Recipe getRecipe(String search) {
		Recipe original = original(search);
		if (original != null) {
			return original;
		}
		
        search = search.toLowerCase();

        Double bestSimilarity = Double.NEGATIVE_INFINITY;
        Recipe bestMatch = null;
		for (Recipe recipe : RecipeManager.getManager().getRecipes()) {
			Double sim = jw.similarity(search, recipe.getName().toLowerCase());
			if (sim > bestSimilarity) {
				bestSimilarity = sim;
				bestMatch = recipe;
			}
		}
		
		return bestMatch;
	}

}
