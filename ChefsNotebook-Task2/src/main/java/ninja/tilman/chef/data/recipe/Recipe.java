package ninja.tilman.chef.data.recipe;

import java.util.Collection;

import com.google.common.base.MoreObjects;

import ninja.tilman.chef.data.base.Feature;
import ninja.tilman.chef.data.base.FeatureComposition;

public class Recipe extends FeatureComposition<Recipe> {
	private final String name;
	private final String description;
	private final String instructions;
	
	public Recipe(String name, String description, String instructions, Collection<Feature<Recipe>> features) {
		super(features);
		this.name = name;
		this.description = description;
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
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("description", description)
				.add("instructions", instructions)
				.add("features", features)
				.toString();
	}
}
