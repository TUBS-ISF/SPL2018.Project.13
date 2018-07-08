package ninja.tilman.chef.cli;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CommandProcessor {	
	public String processCommand(String command) {
		List<String> segments = Arrays.asList(command.split("\\s+"));
		return processCommand(segments);
	}
	
	public String processCommand(List<String> commandSegments) {
		if (!commandSegments.isEmpty()) {
			String subcommand = commandSegments.get(0);
			
			for (SubCommand sc : getSubCommands()) {
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
		for (SubCommand sc : getSubCommands()) {
			error.append("\t");
			error.append(sc.getSubCommandIdentifier());
			error.append(" - ");
			error.append(sc.getDocumentation());
			error.append("\n");
		}
		return error.toString();
	}
	
	private List<SubCommand> getSubCommands() {
		return new ArrayList<SubCommand>();
	}
}
