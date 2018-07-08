import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.Node;

import ninja.tilman.chef.data.IngredientsStructured;
import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.manager.RecipeManager;
import ninja.tilman.chef.manager.MarkdownFiles;

public aspect MarkdownIngredientsStructured {
	void RecipeManager.setIngredients(Recipe recipe, MarkdownFiles.Section ingredientsSection) {
		ImmutableList.Builder<IngredientsStructured.Ingredient> ingredientsList = ImmutableList.builder();
		for (Node node : ingredientsSection.getNodes()) {
			if (node instanceof ListBlock) {
				for (Node child : node.getChildren()) {
					ingredientsList.add(createIngredientFromNode(child));
				}
			} else {
				ingredientsList.add(createIngredientFromNode(node));
			}
		}
		
		recipe.setIngredients(ingredientsList.build());
	}
	
	IngredientsStructured.Ingredient RecipeManager.createIngredientFromNode(Node node) {
		StringBuilder textBuilder = new StringBuilder();
		List<IngredientsStructured.Ingredient> children = new ArrayList<IngredientsStructured.Ingredient>();
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

	IngredientsStructured.Ingredient RecipeManager.createIngredient(List<IngredientsStructured.Ingredient> children, String text, Node node) {
		// TODO node in ingredients
		return new IngredientsStructured.Ingredient(text, children);
	}
}