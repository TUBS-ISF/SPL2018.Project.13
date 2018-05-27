package ninja.tilman.chef.cli;

import java.util.Arrays;
import java.util.List;

import info.debatty.java.stringsimilarity.JaroWinkler;
import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.manager.RecipeManager;
import ninja.tilman.chef.manager.RecipeManagerProvider;

public class CommandProcessor {
	private final RecipeManager manager;
	
	public CommandProcessor() {
		manager = RecipeManagerProvider.getManager();
	}
	
	public String processCommand(String command) {
		List<String> segments = Arrays.asList(command.split("\\s+"));
		return processCommand(segments);
	}
	
	public String processCommand(List<String> commandSegments) {
		if (!commandSegments.isEmpty()) {
			String subcommand = commandSegments.get(0);
			List<String> arguments = commandSegments.subList(1, commandSegments.size());
			
			// #if ListRecipes
			if ("list".equals(subcommand)) {
				return listRecipes(arguments);
			}
			// #endif
			// #if ShowRecipe
			if ("show".equals(subcommand)) {
				return showRecipe(arguments);
			}
			// #endif
		}
		
		
		return createNotRecognizedCommandError(commandSegments);
	}
	
	// #if ListRecipes
	private String listRecipes(List<String> arguments) {
		StringBuilder result = new StringBuilder();
		for (Recipe recipe : manager.getRecipes()) {
			result.append(recipe.getName());
			result.append("\n");
		}
		return result.toString();
	}
	// #endif
	
	// #if ShowRecipe
	private String showRecipe(List<String> arguments) {
		// #if SelectByName
		String search = String.join(" ", arguments);
        JaroWinkler jw = new JaroWinkler();
        
        Double bestSimilarity = Double.NEGATIVE_INFINITY;
        Recipe bestMatch = null;
		for (Recipe recipe : manager.getRecipes()) {
			Double sim = jw.similarity(search, recipe.getName());
			if (sim > bestSimilarity) {
				bestSimilarity = sim;
				bestMatch = recipe;
			}
		}
		return bestMatch.getName();
		// #endif
	}
	// #endif
	

	private String createNotRecognizedCommandError(List<String> commandSegments) {
		StringBuilder error = new StringBuilder();
		if (commandSegments.isEmpty()) {
			error.append("You need to specify one of the following subcommands!");
		} else {
			error.append("Command \""+commandSegments.get(0)+"\" not recognized! The following commands are available:");
		}
		error.append("\n");
		// #if ListRecipes
		error.append("\tlist - List all recipes\n");
		// #endif
		// #if ShowRecipe
		error.append("\tshow - Show all recipes\n");
		// #endif
		return error.toString();
	}
}
