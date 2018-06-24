package ninja.tilman.chef.manager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.Node;

import ninja.tilman.chef.data.Ingredient;
import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.data.RecipeBuilder;

public class RecipeManager {
	
	private void setIngredients(RecipeBuilder builder, Section ingredientsSection) {
		List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
		for (Node node : ingredientsSection.getNodes()) {
			if (node instanceof ListBlock) {
				for (Node child : node.getChildren()) {
					ingredientsList.add(createIngredientFromNode(child));
				}
			} else {
				ingredientsList.add(createIngredientFromNode(node));
			}
		}
		
		builder.setIngredients(ingredientsList);
	}
	
	private Ingredient createIngredientFromNode(Node node) {
		StringBuilder textBuilder = new StringBuilder();
		List<Ingredient> children = new ArrayList<Ingredient>();
		for (Node child : node.getChildren()) {
			if (child instanceof ListBlock) {
				for (Node listItem : child.getChildren()) {
					children.add(createIngredientFromNode(listItem));
				}
			} else {
				textBuilder.append(child.getChars());
			}
		}
		String text = textBuilder.toString().trim();
		return createIngredient(children, text, node);
	}

	private Ingredient createIngredient(List<Ingredient> children, String text, Node node) {
		// TODO node in ingredients
		return new Ingredient(text, children);
	}
}