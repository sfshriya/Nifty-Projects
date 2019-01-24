
//details of each linkid
//contains link description, title, and published date

import java.util.ArrayList;

public class LinkDetails {

	private String link;
	private String title;
	private Integer linkId;
	private String description;
	private String pubdate;

	public String getLink() {
		return this.link;
	}

	public String getDescription() {
		return this.description;
	}

	public String getPubdate() {
		return this.pubdate;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getLinkId() {
		return this.linkId;
	}

	public LinkDetails(String link, String title, String description, String pubdate) {
		this.link = link;
		this.title = title;
		this.linkId = linkId;
		this.description = description;
		this.pubdate = pubdate;
	}

}
