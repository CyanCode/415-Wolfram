import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class RequestProcessor {
	private String query;
	
	public RequestProcessor(String query) {
		this.query = query;
	}
	
	/**
	 * Queries the WolframAlpha server and returns the XML response
	 * @return XML response
	 */
	private String queryServer() {
		String fullURL = "http://api.wolframalpha.com/v2/query?input=" + query + "&appid=" + ServerKey.getKey();
		try {
			Document doc = Jsoup.connect(fullURL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseDocument(String xml) {
		
	}
}
