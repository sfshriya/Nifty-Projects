
//Search Algoritm for search for word

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.IOUtils;

public class LinkSearch {
	// to store word and link entries with word count
	private static HashMap<String, List<LinkEntry>> wordToLinkDict;
	// to store link id vs link details
	private static HashMap<Integer, LinkDetails> LinkIdToLinkDict;
	private String rssURL;
	private RSSLinkProcess rsslink;
	private static Integer linkId;
	// to make sure we do not parse repeating links again and again
	private static HashSet<String> uniqueLinks;
	private static HashSet<String> commonWds;

	public LinkSearch(String rssURL) {
		// for linkid increment
		linkId = 0;
		wordToLinkDict = new HashMap<String, List<LinkEntry>>();
		LinkIdToLinkDict = new HashMap<Integer, LinkDetails>();
		this.rssURL = rssURL;
		rsslink = new RSSLinkProcess();
		uniqueLinks = new HashSet<String>();
		commonWds = new HashSet<String>();
	}

	// start processing RSS URL to get links and titles and descriptions
	public void processRSSLink() {

		Integer howManyWords = 0;

		getCommonWords();

		// System.out.println("we are here");
		// Integer totalLinks =0;

		// scrapeRSSPage For getting Links in RSS page
		List<LinkDetails> rssProcessedData = rsslink.scrapeRSSPageForLinks(rssURL);

		// now process one link at a time
		for (LinkDetails linkData : rssProcessedData) {
			// System.out.println(linkData.getDescription());
			// System.out.println(linkData.getLink());
			// to keep track of word and its counts in each link
			HashMap<String, Integer> wordcounts = new HashMap<String, Integer>();

			if (!uniqueLinks.contains(linkData.getLink())) {
				// totalLinks++;
				uniqueLinks.add(linkData.getLink());

				// System.out.println(linkData.getDescription() + " total links" +totalLinks);

				// increment linkidCounter for next linkid. We use linkid rather than string to
				// track id
				linkId++;
				// add link to linkdictionary so that we store linkid vs all details of the link
				LinkIdToLinkDict.put(linkId, linkData);
				// process each web page and get the work list out
				String[] words = getWordsFromString(linkData.getDescription());
				for (String word : words) {
					// get the words and counts for the current link
					// System.out.println(word);
					// if its a common word do not add to the counts
					if (commonWds.contains(word))
						continue;

					if (wordcounts.containsKey(word.toLowerCase())) {
						wordcounts.put(word.toLowerCase(), wordcounts.get(word.toLowerCase()) + 1);
					} else {
						if ((word.trim().length() > 1)) {
							wordcounts.put(word.toLowerCase(), 1);
						}
					}
				}
				words = getWordsFromString(linkData.getTitle());

				for (String word : words) {
					// get the words and counts for the current link
					// System.out.println(word);
					if (commonWds.contains(word))
						continue;

					if (wordcounts.containsKey(word.toLowerCase())) {
						wordcounts.put(word.toLowerCase(), wordcounts.get(word.toLowerCase()) + 1);
					} else {
						if ((word.trim().length() > 1)) {
							wordcounts.put(word.toLowerCase(), 1);
						}
					}
				}

				// now parse the Web page and get content
				String pageWords = rsslink.ProcessHTMLPage(linkData.getLink());

				words = getWordsFromString(pageWords);

				for (String word : words) {
					// get the words and counts for the current link
					// System.out.println(word);
					if (commonWds.contains(word))
						continue;

					if (wordcounts.containsKey(word.toLowerCase())) {
						wordcounts.put(word.toLowerCase(), wordcounts.get(word.toLowerCase()) + 1);
					} else {
						if ((word.trim().length() > 1)) {
							wordcounts.put(word.toLowerCase(), 1);
						}
					}
				}
			}

			// now we have words and wordcount of each one of the words for this link
			// we now create a link entry for each word with link id and count

			for (HashMap.Entry<String, Integer> entry : wordcounts.entrySet()) {
				// System.out.println(entry.getKey());
				// System.out.println(entry.getValue());
				// for every word create an entry of the link
				LinkEntry linkEntry = new LinkEntry(linkId, entry.getValue());
				List<LinkEntry> linkEntriesByWord;

				if (!wordToLinkDict.containsKey(entry.getKey())) {
					linkEntriesByWord = new ArrayList<LinkEntry>();

				} else {
					linkEntriesByWord = wordToLinkDict.get(entry.getKey());
				}
				linkEntriesByWord.add(linkEntry);
				wordToLinkDict.put(entry.getKey(), linkEntriesByWord);
			}

		}
		// now sort the word counts by word to get descending order of word occurances

		for (HashMap.Entry<String, List<LinkEntry>> entry : wordToLinkDict.entrySet()) {
			howManyWords++;
			Collections.sort(entry.getValue(), Collections.reverseOrder());
		}

		System.out.println(howManyWords);

	}

	// search for links that contain word

	public void wordSearch(String word) {

		List<LinkEntry> resultLinks = wordToLinkDict.get(word.toLowerCase());

		if (resultLinks == null || resultLinks.isEmpty()) {
			System.out.println("0 links found for: " + word);
			return;

		}

		Integer searchResultCount = 1;

		System.out.print("We found " + resultLinks.size() + " articles with the word " + word);
		if (resultLinks.size() > 10) {
			System.out.println(". [We'll just list 10 of them, though.] ");
		} else
			System.out.println(".");

		for (LinkEntry linkEntry : resultLinks) {

			System.out.println(searchResultCount.toString() + ".) "
					+ LinkIdToLinkDict.get(linkEntry.getLinkId()).getTitle() + " [Search term occurs " +

					linkEntry.getWordCount() + " times]");
			System.out.println(LinkIdToLinkDict.get(linkEntry.getLinkId()).getDescription());

			System.out.println(LinkIdToLinkDict.get(linkEntry.getLinkId()).getLink());
			searchResultCount++;
			// only show top 10 results
			if (searchResultCount > 10)
				return;

		}

	}

	private String[] getWordsFromString(String content) {
		String[] words = content.split("\\W+");
		return words;
	}

	public void getCommonWords() {

		try (FileInputStream inputStream = new FileInputStream("commonwds.txt")) {
			String strFile = IOUtils.toString(inputStream);
			Arrays.stream((strFile.split("\\W+"))).forEach(commonWds::add);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
