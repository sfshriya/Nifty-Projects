
//Linkentry for hashmap that holds link id and wordcount of occurances of the word in the link

import java.util.*;

public class LinkEntry implements Comparable<LinkEntry> {

	private Integer linkId;
	private int wordCount;

	public Integer getLinkId() {
		return this.linkId;
	}

	public int getWordCount() {
		return this.wordCount;
	}

	public LinkEntry(Integer linkId, int wordCount) {
		this.wordCount = wordCount;
		this.linkId = linkId;
	}

	@Override
	public int compareTo(LinkEntry other) {
		return (this.wordCount - other.getWordCount());
	}

}
