package net.mylesputnam.lastfm.api.requests;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class OrderedParamSet implements Iterable<LastFmParam> {
	private final LinkedHashMap<String, LastFmParam> internalMap;
	
	public static OrderedParamSet combine(OrderedParamSet defaultParams, OrderedParamSet overrideParams) {
		OrderedParamSet combinedSets = new OrderedParamSet(defaultParams.toList());
		combinedSets.addAll(overrideParams.toList());
		return combinedSets;
	}
	
	public OrderedParamSet() {
		this.internalMap = new LinkedHashMap<>();
	}
	
	public OrderedParamSet(Collection<LastFmParam> params) {
		this();
		this.addAll(params);
	}
	
	public void add(LastFmParam param) {
		if(param != null) {
			this.internalMap.put(param.key, param);
		}
	}
	
	public void addAll(Collection<LastFmParam> params) {
		if(params == null) {
			return;
		}
		
		for(LastFmParam param : params) {
			this.add(param);
		}
	}
	
	public List<LastFmParam> toList() {
		return new LinkedList<>(internalMap.values());
	}

	@Override
	public Iterator<LastFmParam> iterator() {
		final Iterator<String> internalIterator = internalMap.keySet().iterator();
		return new Iterator<LastFmParam>() {
			@Override
			public boolean hasNext() {
				return internalIterator.hasNext();
			}

			@Override
			public LastFmParam next() {
				return internalMap.get(internalIterator.next());
			}
		};
	}
	
	@Override
	public String toString() {
		return toList().toString();
	}
}
