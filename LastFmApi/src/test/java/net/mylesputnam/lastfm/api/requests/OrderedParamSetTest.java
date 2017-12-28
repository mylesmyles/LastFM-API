package net.mylesputnam.lastfm.api.requests;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class OrderedParamSetTest {
	
	@Test
	public void testEmptyCreation() {
		OrderedParamSet set = new OrderedParamSet();
		Assert.assertNotNull(set.toList());
		Assert.assertTrue(set.toList().isEmpty());
	}
	
	@Test
	public void testCreationFromCollection() {
		Set<LastFmParam> initialElements = new HashSet<>();
		initialElements.add(LastFmParam.apiKey("hi"));
		initialElements.add(LastFmParam.create("user", "mylesmyles07"));
		
		OrderedParamSet set = new OrderedParamSet(initialElements);
		Assert.assertNotNull(set.toList());
		
		Set<LastFmParam> addedElements = new HashSet<>(set.toList());
		Assert.assertTrue(addedElements.contains(LastFmParam.apiKey("hi")));
		Assert.assertTrue(addedElements.contains(LastFmParam.create("user", "mylesmyles07")));
		Assert.assertEquals(2, addedElements.size());
	}
	
	@Test
	public void testCreationFromOrderedCollection() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(LastFmParam.apiKey("hi"), LastFmParam.create("user", "mylesmyles07")));
		Assert.assertNotNull(set.toList());
		
		List<LastFmParam> addedElements = set.toList();
		Assert.assertEquals(2, addedElements.size());
		Assert.assertEquals(LastFmParam.apiKey("hi"), addedElements.remove(0));
		Assert.assertEquals(LastFmParam.create("user", "mylesmyles07"), addedElements.remove(0));
		Assert.assertEquals(2, set.toList().size());
	}
	
	@Test
	public void testIteratorAndListEquivalent() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(LastFmParam.apiKey("hi"), LastFmParam.create("user", "mylesmyles07")));
		Assert.assertNotNull(set.toList());
		
		List<LastFmParam> addedElements = set.toList();
		
		for(LastFmParam param : set) {
			Assert.assertEquals(addedElements.remove(0), param);
		}
		Assert.assertEquals(2, set.toList().size());
	}
	
	@Test
	public void testAddNew() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(LastFmParam.apiKey("hi"), LastFmParam.create("user", "mylesmyles07")));
		set.add(LastFmParam.artist("2814"));
		
		List<LastFmParam> addedElements = set.toList();
		Assert.assertEquals(LastFmParam.apiKey("hi"), addedElements.remove(0));
		Assert.assertEquals(LastFmParam.create("user", "mylesmyles07"), addedElements.remove(0));
		Assert.assertEquals(LastFmParam.artist("2814"), addedElements.remove(0));
	}
	
	@Test
	public void testAddDuplicate() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(LastFmParam.apiKey("hi"), LastFmParam.create("user", "mylesmyles07")));
		set.add(LastFmParam.apiKey("bye"));
		
		List<LastFmParam> addedElements = set.toList();
		Assert.assertEquals(LastFmParam.apiKey("bye"), addedElements.remove(0));
		Assert.assertEquals(LastFmParam.create("user", "mylesmyles07"), addedElements.remove(0));
	}
	
	@Test
	public void testAddNull() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(LastFmParam.apiKey("hi"), LastFmParam.create("user", "mylesmyles07")));
		set.add(null);
		
		List<LastFmParam> addedElements = set.toList();
		Assert.assertEquals(2, addedElements.size());
		Assert.assertEquals(LastFmParam.apiKey("hi"), addedElements.remove(0));
		Assert.assertEquals(LastFmParam.create("user", "mylesmyles07"), addedElements.remove(0));
	}
	
	@Test
	public void testAddAll() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(LastFmParam.apiKey("hi"), LastFmParam.create("user", "mylesmyles07")));
		set.addAll(Arrays.asList(LastFmParam.artist("2814"), LastFmParam.apiKey("bye")));
		
		List<LastFmParam> addedElements = set.toList();
		Assert.assertEquals(3, addedElements.size());
		Assert.assertEquals(LastFmParam.apiKey("bye"), addedElements.remove(0));
		Assert.assertEquals(LastFmParam.create("user", "mylesmyles07"), addedElements.remove(0));
		Assert.assertEquals(LastFmParam.artist("2814"), addedElements.remove(0));
	}
	
	@Test
	public void testAddAllWithNull() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(LastFmParam.apiKey("hi"), LastFmParam.create("user", "mylesmyles07")));
		set.addAll(null);
		
		List<LastFmParam> addedElements = set.toList();
		Assert.assertEquals(2, addedElements.size());
		Assert.assertEquals(LastFmParam.apiKey("hi"), addedElements.remove(0));
		Assert.assertEquals(LastFmParam.create("user", "mylesmyles07"), addedElements.remove(0));
	}
}
