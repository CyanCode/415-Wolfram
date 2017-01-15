package com.walkerchristie.content;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RequestProcessor {
	private Pod inputPod, firstPod;
	private String currentUrl;
	
	private ArrayList<Pod> extraPods = new ArrayList<>();
	private ArrayList<Assumption> assumptions = new ArrayList<>();

	/**
	 * Queries the server and returns the initial response from 
	 * the server for the query associated with this request
	 * @return Response from the server
	 */
	private String getResponse() {
		try {
			parseDocument(queryServer());
		} catch (IOException e) {
			e.printStackTrace(); // TODO Auto-generated catch block
		}

		String result = "";
		result += inputPod == null ? "" : "\n" + inputPod.toString();

		for (Assumption assumption : assumptions) {
			result += '\n' + assumption.toString();
		}

		result += firstPod == null ? "" : "\n" + firstPod.toString();

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

	public String respondTo(String message) {
		int choice = numberFrom(message);

		if (choice > 0 && choice <= extraPods.size()) {
			return extraPods.get(choice - 1).toString();
		} else if (assumptions.size() > 0 && choice == extraPods.size() + 1) {
			//TODO: Show assumptions
		} else if (choice > assumptions.size() + 1 && choice < assumptions.size() + extraPods.size() + 1) {
			url += "&assumption=" + assumptions.get(choice - extraPods.size() - 1).getURL(numOfAssumption);
			return getResponse();
		} else {
			currentUrl = "http://api.wolframalpha.com/v2/query?input=" + message + "&appid=" + ServerKey.getWolframKey();
			return getResponse();
		}
	}

	public boolean validRequest(String request) {
		try {
			int requestInt = Integer.valueOf(request);
			return (requestInt > 0 && requestInt < extraPods.size() + 1);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Queries the WolframAlpha server and returns the XML response
	 * @return XML response
	 */
	private Document queryServer() throws IOException {
		return Jsoup.connect(currentUrl).timeout(1000 * 10).get();
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

	private int numberFrom(String num) {
		try {
			return Integer.parseInt(num);
		} catch (Exception e) {
			try {
				return Integer.parseInt(num.substring(0, num.length() - 1));
			} catch (Exception e2) {
				return -1;
			}
		}
	}
}