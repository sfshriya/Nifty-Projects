//RSSReader Shriya Kagolanu; Stanford Nifty Project 
// http://nifty.stanford.edu/2006/cain-rss/
/*Algorithm Steps:
Step 1: Open XML page of RSS feed
Parse the XML to get 
Algorithm: Read the links in RSS XML feed
Step 1: for each link crawl the page and get words in the page
Step 2: for each word (hashmap)  build a list of linkids that have the word -
sorted by descending order of no of occurrences of the word in the link
exclude common words like is, was, for, she , he etc
Step 3: Search by word - get the linkids and get the link details of each word
for now I've just added BBC's news feed but you can change the link or send it as a command parameter
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {

		LinkSearch linkSearch = new LinkSearch("http://feeds.bbci.co.uk/news/rss.xml");

		System.out.println("Processing BBC rss feed http://feeds.bbci.co.uk/news/rss.xml");

		linkSearch.processRSSLink();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String searchWord = null;
			System.out.print("Please enter a single search term [enter to break]: ");

			try {
				searchWord = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Searching for word " + searchWord + " .....");

			linkSearch.wordSearch(searchWord.toLowerCase());
			continue;
		}

	}

}
