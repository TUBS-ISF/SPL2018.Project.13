import java.util.Collection;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;
import ninja.tilman.chef.data.Recipe;

public aspect IngredientsStructured {
	public ImmutableList<Ingredient> Recipe.ingredients;
	
	public ImmutableList<Ingredient> Recipe.getIngredients() {
		return ingredients;
	} 
	
	public void Recipe.setIngredients(ImmutableList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	} 
	
	String around(Recipe it): call(String Recipe.getIngredientsText()) && target(it) {
		StringBuilder b = new StringBuilder();
		buildIngredientsText(b, it.getIngredients(), 0);
		return b.toString();
	}
	
	private void buildIngredientsText(StringBuilder b, ImmutableList<Ingredient> ingredients, int depth) {
		for (Ingredient ingredient : ingredients) {
			for (int i = 0; i < depth; i++) {
				b.append('\t');
			}
			b.append("- ");
			b.append(ingredient.getText().trim());
			b.append("\n");
			buildIngredientsText(b, ingredient.getChildren(), depth + 1);
		}
	}
	
	public static class Ingredient {
		private final String name;
		private final ImmutableList<Ingredient> children;
		
		public Ingredient(String name) {
			this(name, ImmutableList.of());
		}
		
		public Ingredient(String name, Collection<Ingredient> children) {
			this.name = name;
			this.children = ImmutableList.copyOf(children);
			
			assert name != null;
			assert children != null;
		}
		
		public String getName() {
			return name;
		}
		
		public String getText() {
			return name;
		}

		public ImmutableList<Ingredient> getChildren() {
			return children;
		}
		
		public String toString() {
			ToStringHelper helper = MoreObjects.toStringHelper(this).add("name", name);
			if (!children.isEmpty()) {
				helper.add("children", children);
			}
			return helper.toString();
		}
	}

}