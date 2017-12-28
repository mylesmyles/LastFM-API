package net.mylesputnam.lastfm.api.requests;

import java.util.Arrays;
import java.util.List;

public class LastFmRequestBuilder {
	public static final String DEFAULT_URL = "http://ws.audioscrobbler.com/2.0/";
	
	private String url;
	private boolean isJsonFormat;
	private final OrderedParamSet defaultParams;
	
	public static LastFmRequestBuilder create(String apiKey) {
		return new LastFmRequestBuilder(DEFAULT_URL, LastFmParam.apiKey(apiKey));
	}
	
	public LastFmRequestBuilder withUrl(String url) {
		this.url = url;
		return this;
	}
	
	public LastFmRequestBuilder withJsonFormat() {
		isJsonFormat = true;
		return this;
	}
	
	public LastFmRequestBuilder withXmlFormat() {
		isJsonFormat = false;
		return this;
	}
	
	public LastFmRequestBuilder withMethod(String method) {
		defaultParams.add(LastFmParam.method(method));
		return this;
	}
	
	public LastFmRequestBuilder withDefaultParam(LastFmParam defaultParam) {
		defaultParams.add(defaultParam);
		return this;
	}
	
	private LastFmRequestBuilder(String url, LastFmParam...defaultParams) {
		this.url = url;
		this.isJsonFormat = false;
		this.defaultParams = new OrderedParamSet(Arrays.asList(defaultParams));
	}
	
	public LastFmRequest buildRequest(LastFmParam...params) {
		return buildRequest(Arrays.asList(params));
	}
	
	public LastFmRequest buildRequest(final List<LastFmParam> params) {
		OrderedParamSet paramSet = new OrderedParamSet(params);
		if(isJsonFormat) {
			paramSet.add(LastFmParam.jsonFormat());
		}
		
		OrderedParamSet combined = OrderedParamSet.combine(defaultParams, paramSet);
		List<LastFmParam> allParams = combined.toList();
		return BasicLastFmRequest.create(url, allParams);
	}
}
