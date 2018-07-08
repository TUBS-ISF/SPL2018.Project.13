import java.util.List;

import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.cli.RecipeSelector;
import ninja.tilman.chef.cli.SubCommand;

public aspect ShowRecipe {
	public static class ShowRecipeSubCommand implements SubCommand {
		private RecipeSelector recipeSelector = new RecipeSelector();

		@Override
		public String getSubCommandIdentifier() {
			return "show";
		}

		@Override
		public String getDocumentation() {
			return "Show a recipe";
		}

		@Override
		public String execute(List<String> arguments) {
			String search = String.join(" ", arguments);
			Recipe recipe = recipeSelector.getRecipe(search);
			if (recipe == null) {
				return "No recipe found";
			} else {
				return recipe.getText();
			}
		}
	}

	
	List<SubCommand> around(): call(List<SubCommand> getSubCommands()) {
		List<SubCommand> original = proceed();
		original.add(new ShowRecipeSubCommand());
		return original;
    }
}
