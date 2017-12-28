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
		Set<RequestParam> initialElements = new HashSet<>();
		initialElements.add(RequestParam.apiKey("hi"));
		initialElements.add(RequestParam.create("user", "mylesmyles07"));
		
		OrderedParamSet set = new OrderedParamSet(initialElements);
		Assert.assertNotNull(set.toList());
		
		Set<RequestParam> addedElements = new HashSet<>(set.toList());
		Assert.assertTrue(addedElements.contains(RequestParam.apiKey("hi")));
		Assert.assertTrue(addedElements.contains(RequestParam.create("user", "mylesmyles07")));
		Assert.assertEquals(2, addedElements.size());
	}
	
	@Test
	public void testCreationFromOrderedCollection() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(RequestParam.apiKey("hi"), RequestParam.create("user", "mylesmyles07")));
		Assert.assertNotNull(set.toList());
		
		List<RequestParam> addedElements = set.toList();
		Assert.assertEquals(2, addedElements.size());
		Assert.assertEquals(RequestParam.apiKey("hi"), addedElements.remove(0));
		Assert.assertEquals(RequestParam.create("user", "mylesmyles07"), addedElements.remove(0));
		Assert.assertEquals(2, set.toList().size());
	}
	
	@Test
	public void testIteratorAndListEquivalent() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(RequestParam.apiKey("hi"), RequestParam.create("user", "mylesmyles07")));
		Assert.assertNotNull(set.toList());
		
		List<RequestParam> addedElements = set.toList();
		
		for(RequestParam param : set) {
			Assert.assertEquals(addedElements.remove(0), param);
		}
		Assert.assertEquals(2, set.toList().size());
	}
	
	@Test
	public void testAddNew() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(RequestParam.apiKey("hi"), RequestParam.create("user", "mylesmyles07")));
		set.add(RequestParam.artist("2814"));
		
		List<RequestParam> addedElements = set.toList();
		Assert.assertEquals(RequestParam.apiKey("hi"), addedElements.remove(0));
		Assert.assertEquals(RequestParam.create("user", "mylesmyles07"), addedElements.remove(0));
		Assert.assertEquals(RequestParam.artist("2814"), addedElements.remove(0));
	}
	
	@Test
	public void testAddDuplicate() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(RequestParam.apiKey("hi"), RequestParam.create("user", "mylesmyles07")));
		set.add(RequestParam.apiKey("bye"));
		
		List<RequestParam> addedElements = set.toList();
		Assert.assertEquals(RequestParam.apiKey("bye"), addedElements.remove(0));
		Assert.assertEquals(RequestParam.create("user", "mylesmyles07"), addedElements.remove(0));
	}
	
	@Test
	public void testAddNull() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(RequestParam.apiKey("hi"), RequestParam.create("user", "mylesmyles07")));
		set.add(null);
		
		List<RequestParam> addedElements = set.toList();
		Assert.assertEquals(2, addedElements.size());
		Assert.assertEquals(RequestParam.apiKey("hi"), addedElements.remove(0));
		Assert.assertEquals(RequestParam.create("user", "mylesmyles07"), addedElements.remove(0));
	}
	
	@Test
	public void testAddAll() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(RequestParam.apiKey("hi"), RequestParam.create("user", "mylesmyles07")));
		set.addAll(Arrays.asList(RequestParam.artist("2814"), RequestParam.apiKey("bye")));
		
		List<RequestParam> addedElements = set.toList();
		Assert.assertEquals(3, addedElements.size());
		Assert.assertEquals(RequestParam.apiKey("bye"), addedElements.remove(0));
		Assert.assertEquals(RequestParam.create("user", "mylesmyles07"), addedElements.remove(0));
		Assert.assertEquals(RequestParam.artist("2814"), addedElements.remove(0));
	}
	
	@Test
	public void testAddAllWithNull() {
		OrderedParamSet set = new OrderedParamSet(Arrays.asList(RequestParam.apiKey("hi"), RequestParam.create("user", "mylesmyles07")));
		set.addAll(null);
		
		List<RequestParam> addedElements = set.toList();
		Assert.assertEquals(2, addedElements.size());
		Assert.assertEquals(RequestParam.apiKey("hi"), addedElements.remove(0));
		Assert.assertEquals(RequestParam.create("user", "mylesmyles07"), addedElements.remove(0));
	}
}
