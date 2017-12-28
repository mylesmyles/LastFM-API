package net.mylesputnam.lastfm.api.requests;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class LastFmRequestBuilderTest {
	
	@Test
	public void testCreate() {
		LastFmRequestBuilder builder = LastFmRequestBuilder.create("hi");
		
		LastFmRequest request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi", request.getUrl());
	}
	
	@Test
	public void testWithUrl() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withUrl("https://mylesputnam.net");
		
		LastFmRequest request = builder.buildRequest();
		Assert.assertEquals("https://mylesputnam.net?api_key=hi", request.getUrl());
	}
	
	@Test
	public void testJsonFormat() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withJsonFormat();
		
		LastFmRequest request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi&format=json", request.getUrl());
	}
	
	@Test
	public void testXmlFormat() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withXmlFormat();
		
		LastFmRequest request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi", request.getUrl());
		
		builder.withJsonFormat();
		builder.withXmlFormat();
		request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi", request.getUrl());
	}
	
	@Test
	public void testMethod() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withMethod("none");
		
		LastFmRequest request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi&method=none", request.getUrl());
	}
	
	@Test
	public void testDefaultParam() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withDefaultParam(RequestParam.artist("Yes"));
		
		LastFmRequest request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi&artist=Yes", request.getUrl());
	}
	
	@Test
	public void testParamOrder() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withDefaultParam(RequestParam.artist("Yes"))
				.withJsonFormat();
		
		LastFmRequest request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi&artist=Yes&format=json", request.getUrl());
		
		builder.withDefaultParam(RequestParam.artist("Rush"));
		request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi&artist=Rush&format=json", request.getUrl());
	}
	
	@Test
	public void testDefaultOverrides() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withDefaultParam(RequestParam.artist("Yes"))
				.withMethod("have_fun");
		
		LastFmRequest request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi&artist=Yes&method=have_fun", request.getUrl());
		
		builder.withDefaultParam(RequestParam.artist("Rush"));
		builder.withDefaultParam(RequestParam.create("user", "mylesmyles07"));
		request = builder.buildRequest();
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi&artist=Rush&method=have_fun&user=mylesmyles07", request.getUrl());
	}
	
	@Test
	public void testPassedInOverrides() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withDefaultParam(RequestParam.artist("Yes"))
				.withMethod("have_fun");
		
		LastFmRequest request = builder.buildRequest(RequestParam.artist("Blur"));
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=hi&artist=Blur&method=have_fun", request.getUrl());
		
		request = builder.buildRequest(RequestParam.create("user", "mylesmyles07"), RequestParam.apiKey("bye"));
		Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=bye&artist=Yes&method=have_fun&user=mylesmyles07", request.getUrl());
	}
	
	@Test
	public void testParamsPassedInAsList() {
		LastFmRequestBuilder builder = LastFmRequestBuilder
				.create("hi")
				.withDefaultParam(RequestParam.artist("Yes"))
				.withMethod("have_fun");
		
		LastFmRequest requestFromArray = builder.buildRequest(RequestParam.create("user", "mylesmyles07"), RequestParam.apiKey("bye"));
		LastFmRequest requestFromList = builder.buildRequest(Arrays.asList(RequestParam.create("user", "mylesmyles07"), RequestParam.apiKey("bye")));
		Assert.assertEquals(requestFromArray.getUrl(), requestFromList.getUrl());
	}
}
