package ninja.tilman.chef.data;

import com.google.common.base.MoreObjects;

public class Recipe {
	
	private String name;
	private String description;
	private String instructions;
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public String getInstructions() {
		return instructions;
	}
	
	public String getIngredientsText() {
		throw new UnsupportedOperationException();
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("description", description)
				.add("instructions", instructions)
				.add("ingredients", getIngredientsText())
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
