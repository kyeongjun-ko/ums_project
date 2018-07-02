package com.bccns.umsserviceweb.ums.vo;

public class URL {
	private String scheme;
	private String host;
	private int port;
	private String uri;
	public URL(String scheme, String host, int port, String uri) {
		super();
		this.scheme = scheme;
		this.host = host;
		this.port = port;
		this.uri = uri;
	}
	public String getScheme() {
		return scheme;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getUri() {
		return uri;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("URL [scheme=");
		builder.append(scheme);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", uri=");
		builder.append(uri);
		builder.append("]");
		return builder.toString();
	}
}
