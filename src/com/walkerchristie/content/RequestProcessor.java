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
	private String[][] assumptionOptions = new String[0][0];
	private ArrayList<String> assumptionStatements = new ArrayList<>();

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

		for (String statement : assumptionStatements) {
			result += '\n' + statement;
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

		if (assumptionOptions.length > 0) {
			result += "\n" + optionNumber + ". Assumption Options";
		}

		return result.equals("") ? "" : result.substring(1);
	}

	public String respondTo(String message) {
		int choice = numberFrom(message);

		if (choice > 0 && choice <= extraPods.size()) {
			return extraPods.get(choice - 1).toString();
		} else if (assumptionOptions.length > 0 && choice == extraPods.size() + 1) {
			return getAssumptionOptions();
		} else if (choice > assumptionOptions.length + 1 && choice < assumptionOptions.length + extraPods.size() + 1) {
			currentUrl += "&assumption=" + assumptionOptions[1][(choice - extraPods.size() - 1)];
			return getResponse();
		} else {
			currentUrl = "http://api.wolframalpha.com/v2/query?input=" + message + "&appid=" + ServerKey.getWolframKey();
			return getResponse();
		}
	}

	private String getAssumptionOptions() {
		String result = "";
		for (int i = 0; i < assumptionOptions.length; i++) {
			result += (i + 1 + extraPods.size()) + ". " + assumptionOptions[0][i];
		}
		return result;
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
		assumptionOptions = new String[0][0];
		assumptionStatements = new ArrayList<>();
		for (Element element : doc.select("assumption")) {
			AssumptionParser assumptionParser = new AssumptionParser(element);
			this.assumptionStatements.add((assumptionParser.getStatement()));
			this.assumptionOptions = assumptionParser.getAssumptionOptions();
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