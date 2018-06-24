package ninja.tilman.chef.data;

import com.google.common.base.MoreObjects;

public class Recipe {
	
	private final String name;
	private final String description;
	private final String instructions;
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public String getInstructions() {
		return instructions;
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("description", description)
				.add("instructions", instructions)
				.add("ingredients", ingredients)
				.toString();
	}
}
