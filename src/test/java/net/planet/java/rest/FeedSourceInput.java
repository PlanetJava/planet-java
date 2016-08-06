package net.planet.java.rest;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/5/16.
 */
public class FeedSourceInput {
	private String url;
	private String siteName;
	private String description;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public String getSiteName() {
		return siteName;
	}

	public String getDescription() {
		return description;
	}
}
