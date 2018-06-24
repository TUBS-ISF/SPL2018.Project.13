package ninja.tilman.chef.cli;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CommandProcessor {	
	private List<SubCommand> getSubCommands() {
		List<SubCommand> original = original();
		original.add(new ShowRecipeSubCommand());
		return original;
	}
}
