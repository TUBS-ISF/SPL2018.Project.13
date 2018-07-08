package ninja.tilman.chef.cli;

import java.util.List;

public interface SubCommand {
	public String getSubCommandIdentifier();
	public String getDocumentation();
	public String execute(List<String> arguments);
}
