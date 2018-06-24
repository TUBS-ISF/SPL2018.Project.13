package ninja.tilman.chef.data;

public class RecipeBuilder {
	private String name;
	private String description;
	private String instructions;
	
	public RecipeBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public RecipeBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public RecipeBuilder setInstructions(String instructions) {
		this.instructions = instructions;
		return this;
	}
}
