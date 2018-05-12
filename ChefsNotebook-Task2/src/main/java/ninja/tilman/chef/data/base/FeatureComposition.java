package ninja.tilman.chef.data.base;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class FeatureComposition<B> {
	protected final Map<Class<? extends Feature<B>>, Feature<B>> features;
	
	@SuppressWarnings("unchecked")
	public FeatureComposition(Collection<Feature<B>> featureCollection) {
		// God damn you, Type Erasure! 
		features = Maps.uniqueIndex(featureCollection, f -> (Class<? extends Feature<B>>) f.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Feature<B>> T getFeature(Class<T> featureClazz) {
		Feature<B> feature = features.get(featureClazz);
		if (feature == null) {
			throw new IllegalArgumentException();
		}
		return (T) feature;
	}
}
