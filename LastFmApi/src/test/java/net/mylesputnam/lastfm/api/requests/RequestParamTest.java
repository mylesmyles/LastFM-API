package net.mylesputnam.lastfm.api.requests;

import org.junit.Assert;
import org.junit.Test;

public class RequestParamTest {
	
	@Test
	public void testCreate() {
		RequestParam param = RequestParam.create("hello", "goodbye");
		Assert.assertEquals("hello", param.key);
		Assert.assertEquals("goodbye", param.value);
	}
	
	@Test
	public void testEqualsAndHashCode() {
		RequestParam param = RequestParam.create("hello", "goodbye");
		Assert.assertEquals(param, RequestParam.create("hello", "goodbye"));
		Assert.assertEquals(param.hashCode(), RequestParam.create("hello", "goodbye").hashCode());
		Assert.assertNotEquals(param, RequestParam.create("not", "equal"));
		Assert.assertNotEquals(param, RequestParam.create("hello", "equal"));
		Assert.assertNotEquals(param, RequestParam.create("not", "goodbye"));
	}
	
	@Test
	public void testApiKey() {
		Assert.assertEquals(RequestParam.create("api_key", "hi"), RequestParam.apiKey("hi"));
	}
	
	@Test
	public void testJsonFormat() {
		Assert.assertEquals(RequestParam.create("format", "json"), RequestParam.jsonFormat());
	}
	
	@Test
	public void testJsonCallback() {
		Assert.assertEquals(RequestParam.create("callback", "hi"), RequestParam.jsonCallback("hi"));
	}
	
	@Test
	public void testMethod() {
		Assert.assertEquals(RequestParam.create("method", "hi"), RequestParam.method("hi"));
	}
	
	@Test
	public void testArtist() {
		Assert.assertEquals(RequestParam.create("artist", "the frogs"), RequestParam.artist("the frogs"));
	}
}
