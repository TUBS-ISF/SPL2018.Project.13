package ninja.tilman.chef.data.ingredients;

public class TextIngredients implements Ingredients {
	private final String ingredients;

	public TextIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getIngredients() {
		return ingredients;
	}
	
	public String toString() {
		return ingredients;
	}
}
