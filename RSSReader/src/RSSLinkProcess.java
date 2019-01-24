
//java xml parser for parsing the XML RSS feed link
//jsoup for parsing the html source  of each link to get only the content

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RSSLinkProcess {

	public void RSSLinkProcess() {

	}

	public List<LinkDetails> scrapeRSSPageForLinks(String rssUrl) {

		// initialize RSS tag values
		String description = "";
		String title = "";
		String link = "";
		String pubdate = "";
		URL url;
		InputStream in;

		List<LinkDetails> rssProcessedData = new ArrayList<LinkDetails>();

		try {
			url = new URL(rssUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		try {
			in = url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// create XML input factory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		// read the XML document
		try {

			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			boolean isFeedHeader = true;
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName().getLocalPart();

					// System.out.println(localPart);

					switch (localPart) {

					case "item":
						// if (isFeedHeader) {
						// isFeedHeader = false;
						// System.out.println(" title: "+title+ " description: "+ description + " link:
						// "+link+" pubDate: "+pubdate);
						// }
						event = eventReader.nextEvent();
						break;

					case "title":
						title = getCharacterData(event, eventReader);
						// System.out.println("title: "+title);
						break;
					case "description":
						description = getCharacterData(event, eventReader);
						// System.out.println("description: "+description);
						break;
					case "link":
						link = getCharacterData(event, eventReader);
						System.out.println("link: " + link);
						break;
					case "pubDate":
						pubdate = getCharacterData(event, eventReader);
						// System.out.println("pubDate: "+pubdate);
						break;

					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == ("item")) {

						LinkDetails linkDetails = new LinkDetails(link, title, description, pubdate);
						rssProcessedData.add(linkDetails);
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}

		return rssProcessedData;
	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	public String ProcessHTMLPage(String url) {

		// String urlToProcess = "https://www.bbc.com/news/world-europe-46281048";
		String htmlSource = null;
		System.out.println("processing .." + url);
		StringBuilder stringBuilder = new StringBuilder();
		try {
			htmlSource = getURLSource(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document document = Jsoup.parse(htmlSource);

		org.jsoup.select.Elements allElements = document.getAllElements();
		for (Element element : allElements) {
			if (element.nodeName().matches(
					"^.*(p|h1|h2|h3|h4|h5|h6|title|body|li|a|em|i|ins|big|bdo|b|blockquote|center|mark|small|string|sub|sup|tt|u).*$")) {
				if (!element.ownText().isEmpty()) {
					// System.out.println(element.nodeName()
					// + " " + element.ownText());
					stringBuilder.append(element.ownText());
				}
			}
		}

		return stringBuilder.toString();

	}

	public static String getURLSource(String url) throws IOException {

		URL urlObject = new URL(url);
		URLConnection urlConnection = urlObject.openConnection();
		urlConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		return toString(urlConnection.getInputStream());
	}

	private static String toString(InputStream inputStream) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
			String inputLine;
			StringBuilder stringBuilder = new StringBuilder();
			while ((inputLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(inputLine);
			}

			return stringBuilder.toString();
		}
	}

}
