package net.mylesputnam.lastfm.api.requests;

import org.junit.Assert;
import org.junit.Test;

public class LastFmParamTest {
	
	@Test
	public void testCreate() {
		LastFmParam param = LastFmParam.create("hello", "goodbye");
		Assert.assertEquals("hello", param.key);
		Assert.assertEquals("goodbye", param.value);
	}
	
	@Test
	public void testEqualsAndHashCode() {
		LastFmParam param = LastFmParam.create("hello", "goodbye");
		Assert.assertEquals(param, LastFmParam.create("hello", "goodbye"));
		Assert.assertEquals(param.hashCode(), LastFmParam.create("hello", "goodbye").hashCode());
		Assert.assertNotEquals(param, LastFmParam.create("not", "equal"));
		Assert.assertNotEquals(param, LastFmParam.create("hello", "equal"));
		Assert.assertNotEquals(param, LastFmParam.create("not", "goodbye"));
	}
	
	@Test
	public void testApiKey() {
		Assert.assertEquals(LastFmParam.create("api_key", "hi"), LastFmParam.apiKey("hi"));
	}
	
	@Test
	public void testJsonFormat() {
		Assert.assertEquals(LastFmParam.create("format", "json"), LastFmParam.jsonFormat());
	}
	
	@Test
	public void testJsonCallback() {
		Assert.assertEquals(LastFmParam.create("callback", "hi"), LastFmParam.jsonCallback("hi"));
	}
	
	@Test
	public void testMethod() {
		Assert.assertEquals(LastFmParam.create("method", "hi"), LastFmParam.method("hi"));
	}
	
	@Test
	public void testArtist() {
		Assert.assertEquals(LastFmParam.create("artist", "the frogs"), LastFmParam.artist("the frogs"));
	}
}
