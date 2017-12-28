package net.mylesputnam.lastfm.api.requests;

public final class LastFmParam {
	public final String key;
	public final String value;
	
	public static LastFmParam create(String key, String value) {
		return new LastFmParam(key, value);
	}
	
	public static LastFmParam apiKey(String apiKey) {
		return LastFmParam.create("api_key", apiKey);
	}
	
	public static LastFmParam jsonFormat() {
		return LastFmParam.create("format", "json");
	}
	
	public static LastFmParam jsonCallback(String callback) {
		return LastFmParam.create("callback", callback);
	}
	
	public static LastFmParam method(String method) {
		return LastFmParam.create("method", method);
	}
	
	public static LastFmParam artist(String artist) {
		return LastFmParam.create("artist", artist);
	}
	
	private LastFmParam(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getParamString() {
		return key + "=" + value;
	}
	
	@Override
	public String toString() {
		return getParamString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LastFmParam other = (LastFmParam) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
