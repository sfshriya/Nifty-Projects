//WikiRacer Nifty Project Shriya Kagolanu
import java.util.ArrayList;
import java.util.List;

public class PQEntry implements Comparable<PQEntry>{
	private String link;
	private int priority;
	private List<String> linkPath;
	
	public PQEntry(String link, int priority) {
		this.link = link;
		this.priority = priority;
		linkPath = new ArrayList<String>();
	}
	
	public String getLink() {
		return this.link;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public void setLinkPath(String s) {
		linkPath.add(s.substring(s.lastIndexOf("/") +1));
	}
	
	public List<String> getLinkPath(){
		return linkPath;
	}
	@Override
	public int compareTo(PQEntry other) {
	if (this.getPriority() < other.getPriority()) {
	return -1;
	}
	else return 1;
	}

}
