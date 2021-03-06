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
	
	public String getText() {
		StringBuilder b = new StringBuilder();
		b.append("# ");
		b.append(name.trim());
		b.append("\n\n");
		if (description != null) {
			b.append(description.trim());
			b.append("\n\n---\n\n");
		}
		b.append(getIngredientsText().trim());
		b.append("\n\n---\n\n");
		b.append(instructions.trim());
		return b.toString();
	}
}
