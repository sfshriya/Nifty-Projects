//WikiRacer Nifty Project Shriya Kagolanu
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiScraper {

	//gets wiki links in a page

	public List<String> getPageLinks(String pageURL) {
		//first scrape page source
		String htmlSource;
		try {
			htmlSource = scrapePage(pageURL);
			//System.out.println(htmlSource);

			//get links out
			return getLinksFromHTML(htmlSource);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		List<String> list = new ArrayList<String>();
		return list;

	}

	//for getting the URL source
	private String scrapePage(String pageURL) throws IOException {

		URL url = null;
		try {
			url = new URL(pageURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection urlConnection = null;
		try {
			urlConnection = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		return toHTMLString(urlConnection.getInputStream());
	}

	private String toHTMLString (InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String inputLine;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			while ((inputLine = bufferedReader.readLine()) != null){
				stringBuilder.append(inputLine);
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return stringBuilder.toString();
	}
	public List<String> getLinksFromHTML(String htmlSource)
	{
		//System.out.println(htmlSource);

		Pattern htmltag = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)</a>");
		Pattern link = Pattern.compile("href=\"[^>]*\">");
		List<String> links = new ArrayList<String>();

		Matcher tagmatch = htmltag.matcher(htmlSource);

		while (tagmatch.find()) {
			Matcher matcher = link.matcher(tagmatch.group());
			matcher.find();
			String finallink = matcher.group().replaceFirst("href=\"", "")
					.replaceFirst("\">", "")
					.replaceFirst("\"[\\s]?target=\"[a-zA-Z_0-9]*", "");

			if (valid(finallink))
			{
				//remove title

				links.add(urlCleanup(finallink));
			}
		}


		return links;
	}

	private boolean valid(String s) {
		if (s.matches(".*[:%#].*")) {
			return false;
		}
		else if (s.startsWith("/wiki/"))
		{
			return true;
		}

		else return false;
	}

	private String urlCleanup(String s){

		if (s.contains("\""))
			return s.substring(0, s.indexOf("\""));
		else return s;

	}

}
