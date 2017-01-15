package com.walkerchristie.content;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServerKey {
	private static String wolframKey, twilioKey;

	public static String getWolframKey() {
		//try {
			return "UVYUVP-3H75RTUX6Q";
			//return wolframKey == null ? new String(Files.readAllBytes(Paths.get("wolframkey.txt"))) : wolframKey;
//		} catch (IOException e) {
//			System.out.println("Ensure that you've created a wolframkey.txt file in the root of your project.");
//			e.printStackTrace();
//
//			System.exit(1);
//			return null;
//		}
	}
	public static String getTwilioKey() {
		try {
			return twilioKey == null ? new String(Files.readAllBytes(Paths.get("twiliokey.txt"))) : twilioKey;
		} catch (IOException e) {
			System.out.println("Ensure that you've created a twiliokey.txt file in the root of your project.");
			e.printStackTrace();

			System.exit(1);
			return null;
		}
	}
}
