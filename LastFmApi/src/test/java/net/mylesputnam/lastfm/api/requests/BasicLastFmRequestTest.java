package net.mylesputnam.lastfm.api.requests;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

public class BasicLastFmRequestTest {

	@Test
	public void testNoParams() {
		BasicLastFmRequest request = BasicLastFmRequest.create(
				"http://mylesputnam.net",
				Collections.emptyList());
		Assert.assertEquals("http://mylesputnam.net", request.getUrl());
	}
	
	@Test
	public void testOneParam() {
		BasicLastFmRequest request = BasicLastFmRequest.create(
				"http://mylesputnam.net",
				Arrays.asList(RequestParam.apiKey("hello")));
		Assert.assertEquals("http://mylesputnam.net?api_key=hello", request.getUrl());
	}
	
	@Test
	public void testMultipleParams() {
		BasicLastFmRequest request = BasicLastFmRequest.create(
				"http://mylesputnam.net",
				Arrays.asList(RequestParam.artist("Yes"), RequestParam.jsonFormat()));
		Assert.assertEquals("http://mylesputnam.net?artist=Yes&format=json", request.getUrl());
	}
}
