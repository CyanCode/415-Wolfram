package com.walkerchristie.content;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServerKey {
	private static String key;
	
	public static String getKey() {
		try {
			return key == null ? new String(Files.readAllBytes(Paths.get("key.txt"))) : key;
		} catch (IOException e) {
			System.out.println("Ensure that you've created a key.txt file in the root of your project.");
			e.printStackTrace();
			
			System.exit(1);
			return null;
		}
	}
}
