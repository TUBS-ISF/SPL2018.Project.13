package ninja.tilman.chef;

import java.io.IOException;

import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.manager.RecipeManager;
import ninja.tilman.chef.manager.RecipeManagerProvider;

public class Main {
	public static void main(String[] args) throws IOException {		
		RecipeManager manager = RecipeManagerProvider.getManager();
		for (Recipe recipe : manager.getRecipes()) {
			 System.out.println(recipe.getName());
		}
	}
}
