package ninja.tilman.chef.data;

// #if Structured
import java.util.Collection;
//#endif

import com.google.common.base.MoreObjects;
//#if Structured
import com.google.common.collect.ImmutableList;
//#endif

public class Recipe {
	// #if MarkdownFiles
	private final String recipeText;
	// #endif
	
	private final String name;
	private final String description;
	private final String instructions;
	
	// #if Text
//@	private final String ingredients;
	// #endif
	// #if Structured
	private final ImmutableList<Ingredient> ingredients;
	// #endif
	
	public Recipe(
			// #if MarkdownFiles
			String recipeText,
			// #endif
			String name, String description, String instructions, 
			// #if Text
//@			String ingredients
			// #endif
			// #if Structured
			Collection<Ingredient> ingredients
			// #endif
		) {
		// #if MarkdownFiles
		this.recipeText = recipeText;
		// #endif
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		// #if Text
//@		this.ingredients = ingredients;
		// #endif
		// #if Structured
		this.ingredients = ImmutableList.copyOf(ingredients);
		// #endif
	}

	// #if MarkdownFiles
	public String getRecipeText() {
		return this.recipeText;
	}
	// #else
//@	public String getRecipeText() {
//@		throw new UnsupportedOperationException("Database is not yet implemented");
//@	}	
	// #endif

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public String getInstructions() {
		return instructions;
	}
	
	// #if Text
//@	public String getIngredients() {
//@		return ingredients;
//@	}
	// #endif
	
	// #if Structured
	public ImmutableList<Ingredient> getIngredients() {
		return ingredients;
	}
	// #endif
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("description", description)
				.add("instructions", instructions)
				.add("ingredients", ingredients)
				.toString();
	}
}
