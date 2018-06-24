package ninja.tilman.chef.data; 


import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;

public class Ingredient {
	private final Double amount;
	private final Unit unit;
	
	public Ingredient(String name, Collection<Ingredient> children) {
		this.amount = null;
		this.unit = null;
	}
	
	public Ingredient(Double amount, Unit unit, String name, Collection<Ingredient> children) {
		this.amount = amount;
		this.unit = unit;
		this.name = name;
		this.children = ImmutableList.copyOf(children);
		
		assert name != null;
		assert children != null;
	}
	
	public Ingredient(Double amount, Unit unit, String name) {
		this(amount, unit, name, ImmutableList.of());
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public String getText() {
		List<String> parts = new ArrayList<String>();
		if (amount != null) {
			parts.add(amount.toString());
		}
		if (unit != null) {
			parts.add(unit.getSymbol());
		}
		parts.add(name);
		return String.join(" ", parts);
	}
}