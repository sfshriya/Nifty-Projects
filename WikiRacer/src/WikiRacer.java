//WikiRacer Nifty Project Shriya Kagolanu
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class WikiRacer {
	private String startPage;
	private String endPage;
	private HashSet<String> endPageLinks;
	private HashSet<String> commonLinks; 
	private PriorityQueue commonLinkPQ;
	private HashSet<String> uniqueLinks;
	
	public WikiRacer(String startPage, String endPage) {
		this.startPage = startPage;
		this.endPage = endPage;
		endPageLinks = new HashSet<String>();
		commonLinks = new HashSet<String>();
		commonLinkPQ = new PriorityQueue<PQEntry>();
		uniqueLinks = new HashSet<String>();
		
		this.endPage = "https://wikipedia.org/wiki" + (endPage.substring(endPage.lastIndexOf("/")));
		this.startPage = "https://wikipedia.org/wiki" + (startPage.substring(startPage.lastIndexOf("/")));
		
	}
	public void startSearch()  {
		WikiScraper wikiScraper = new WikiScraper();
		
		//first get endPage URLs
			List<String> links = wikiScraper.getPageLinks(endPage);
			//System.out.println(links.toString());
		
		for(String i : links) {
			endPageLinks.add(i);
		}
		
		//add startPage to priorty queue
		PQEntry entry = new PQEntry(startPage, 0);
		entry.setLinkPath(startPage);
		
		commonLinkPQ.add(entry);
		
		int iter = 0;
		
		while (true) {
			//System.out.println("We are after while");
			 PQEntry newLink = (PQEntry) commonLinkPQ.poll();
			 
			 iter++;
			 
			 links = wikiScraper.getPageLinks(newLink.getLink());
			 
			 //System.out.println(links.toString());
			 int commonLinkCount = 0;
			 
			 for(String s : links) {
				 
				// System.out.println(endPage);
				// System.out.println(("https://wikipedia.org" +s));
				 if(endPage.equalsIgnoreCase("https://wikipedia.org" +s)) {
					 //System.out.println("We found our destination" + s);
					 newLink.setLinkPath(s);
					 System.out.println("Path: " + (newLink.getLinkPath().toString()));
					 return;
				 }
				 //we have to find if it's common link
				 if(endPageLinks.contains(s)) {
					 commonLinkCount++;
					 commonLinks.add(s);
				 }
			 }
			 
			 for(String s: links) {
				 if(!uniqueLinks.contains(s) && commonLinks.contains(s)) {
					 entry = new PQEntry(("https://wikipedia.org" + s), commonLinkCount);
					 for(String previousLink: newLink.getLinkPath()) {
						 entry.setLinkPath(previousLink);
					 }
					 entry.setLinkPath(s);
					 
					 commonLinkPQ.add(entry);
					 uniqueLinks.add(s);
				 }
			 }
		}
		
		
		
	}
	

}
