package ninja.tilman.chef.data; 

public enum Unit {
	KILOGRAMS("kg", null, 1.0),
	GRAMS("g", Unit.KILOGRAMS, 0.001),
	MILLIGRAMS("mg", Unit.KILOGRAMS, 0.000001),
	LITRES("l", null, 1.0),
	MILILITRES("ml", Unit.LITRES, 0.001);
	
	public static Unit getUnitBySymbol(String symbol) {
		for (Unit unit : Unit.values()) {
			if (unit.getSymbol().equals(symbol)) {
				return unit;
			}
		}
		return null;
	}
	
	
	private final String symbol;
	private final Unit base;
	private final Double conversionFactor;
	
	Unit(String symbol, Unit base, Double conversionFactor) {
		assert (base == null && conversionFactor == null) || (base != null && conversionFactor != null);
		this.symbol = symbol;
		this.base = base;
		this.conversionFactor = conversionFactor;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public Unit getBase() {
		return base;
	}

	public Double getConversionFactor() {
		return conversionFactor;
	}
}

