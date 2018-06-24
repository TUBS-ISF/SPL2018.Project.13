package ninja.tilman.chef.manager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.Node;

import ninja.tilman.chef.data.Ingredient;
import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.data.Unit;
import ninja.tilman.chef.data.RecipeBuilder;

public class RecipeManager {
	private Ingredient createIngredient(List<Ingredient> children, String text, Node node) {
		String[] split = text.split("\\s+", 3);
		
		if (split.length != 1) {
			try {
				Double amount = Double.parseDouble(split[0]);
				if (split.length == 3) {
					Unit unit = Unit.getUnitBySymbol(split[1]);
					if (unit == null) {
						return new Ingredient(amount, null, split[1] + " " + split[2]);
					} else {
						return new Ingredient(amount, unit, split[2]);
					}
				} else {
					return new Ingredient(amount, null, split[1]);
				}
			} catch (NumberFormatException e) {
				// meh
			}
		}
		
		return new Ingredient(text, children);
	}
}