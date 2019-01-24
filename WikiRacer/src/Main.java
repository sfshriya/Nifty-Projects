//WikiRacer Nifty Project Shriya Kagolanu
public class Main {

	public static void main(String[] args) {
		//get start and end page from command
		long startTime = System.nanoTime();
		String startPage = args[0];
		String endPage = args[1];
		System.out.println("Start Page: " + startPage.substring(startPage.lastIndexOf("/") + 1));
		System.out.println("End Page: " + endPage.substring(endPage.lastIndexOf("/") + 1));
		WikiRacer wikiRacer = new WikiRacer(startPage, endPage);
		wikiRacer.startSearch();
		long endTime = System.nanoTime();
		System.out.println("Total time of search in seconds: " + ((endTime - startTime) / 1000000000));
		if(args.length < 2) {
			System.out.println("Please give start page URL and end page URL. eg: wikipedia.org/wiki/fruit");
		}

	}

}