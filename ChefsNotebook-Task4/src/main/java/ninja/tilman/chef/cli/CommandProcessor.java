package ninja.tilman.chef.cli;

import java.util.Arrays;
import java.util.List;

import loader.PluginLoader;
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
			
			for (SubCommand sc : PluginLoader.load(SubCommand.class)) {
				if (sc.getSubCommandIdentifier().equals(subcommand)) {
					List<String> arguments = commandSegments.subList(1, commandSegments.size());
					return sc.execute(arguments);
				}
			}
		}
		
		return createNotRecognizedCommandError(commandSegments);
	}
	
	private String createNotRecognizedCommandError(List<String> commandSegments) {
		StringBuilder error = new StringBuilder();
		if (commandSegments.isEmpty()) {
			error.append("You need to specify one of the following subcommands!");
		} else {
			error.append("Command \""+commandSegments.get(0)+"\" not recognized! The following commands are available:");
		}
		error.append("\n");
		for (SubCommand sc : PluginLoader.load(SubCommand.class)) {
			error.append("\t");
			error.append(sc.getSubCommandIdentifier());
			error.append(" - ");
			error.append(sc.getDocumentation());
			error.append("\n");
		}
		return error.toString();
	}
}
