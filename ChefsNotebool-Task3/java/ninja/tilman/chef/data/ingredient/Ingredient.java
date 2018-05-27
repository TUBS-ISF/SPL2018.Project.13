package ninja.tilman.chef.data.ingredient;

import java.util.Collection;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;

public class Ingredient {
	private final String name;
	private final ImmutableList<Ingredient> children;
	
	public Ingredient(String name) {
		this(name, ImmutableList.of());
	}
	
	public Ingredient(String name, Collection<Ingredient> children) {
		this.name = name;
		this.children = ImmutableList.copyOf(children);
	}

	public String getName() {
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
