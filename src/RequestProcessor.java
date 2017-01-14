import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RequestProcessor {
	private String query;

	private Pod inputPod;
	private Pod firstPod;
	private ArrayList<Pod> extraPods = new ArrayList<>();
	private ArrayList<Assumption> assumptions = new ArrayList<>();
	
	public RequestProcessor(String query) {
		this.query = query;
		queryServer();
	}
	
	/**
	 * Queries the WolframAlpha server and returns the XML response
	 * @return XML response
	 */
	private void queryServer() {
		String fullURL = "http://api.wolframalpha.com/v2/query?input=" + query + "&appid=" + ServerKey.getKey();
		try {
			Document doc = Jsoup.connect(fullURL).timeout(1000 * 10).get();
			parseDocument(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseDocument(Document doc) {
		for (Element element : doc.select("pod")) {
			Pod currPod = new Pod(element.attr("title"), element.select("plaintext").text());
			if (currPod.isInput()) {
				inputPod = currPod;
			} else if (firstPod == null) {
				firstPod = currPod;
			} else {
				extraPods.add(currPod);
			}
		}
		for (Element element : doc.select("assumption")) {
			assumptions.add(new Assumption(element));
		}

	}

	public String getResponse() {
		String result = "";
		result += inputPod == null ? "" : "\n" + inputPod.toString();
		result += firstPod == null ? "" : "\n" + firstPod.toString();
		for (Assumption assumption : assumptions) {
			result += '\n' + assumption.toString();
		}
		int optionNumber = 1;
		if (extraPods.size() > 0) {
			result += "\nRespond with a number below for more information";
			for (Pod pod : extraPods) {
				result += "\n" + optionNumber + ". " + pod.getTitle();
				optionNumber++;
			}
		}
		if (assumptions.size() > 0) {
			result += "\n" + optionNumber + ". Assumption Options";
		}
		return result.equals("") ? "" : result.substring(1);
	}
	public String podString(int podNumber) {
		return extraPods.get(podNumber - 1).toString();
	}
	public boolean validRequest(String request) {
		try {
			int requestInt = Integer.valueOf(request);
			return (requestInt > 0 && requestInt < extraPods.size() + 1);
		} catch (Exception e) {
			return false;
		}
	}
}