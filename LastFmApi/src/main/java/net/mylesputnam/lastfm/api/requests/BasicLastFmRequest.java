package net.mylesputnam.lastfm.api.requests;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class BasicLastFmRequest implements LastFmRequest {
	private final String url;
	private final List<LastFmParam> requestParams;
	
	public static BasicLastFmRequest create(String url, List<LastFmParam> requestParams) {
		return new BasicLastFmRequest(url, requestParams);
	}
	
	private BasicLastFmRequest(String url, List<LastFmParam> requestParams) {
		this.url = url;
		this.requestParams = new LinkedList<>(requestParams);
	}

	@Override
	public String getUrl() {
		return buildUrlString();
	}
	
	private String buildUrlString() {
		if(requestParams.isEmpty()) {
			return url;
		}
		else {
			return url + "?" + getParamsAsString(requestParams);
		}
	}
	
	private String getParamsAsString(List<LastFmParam> params) {
		return String.join("&", params.stream().map(param -> param.getParamString())
				.collect(Collectors.toList()));
	}
}
