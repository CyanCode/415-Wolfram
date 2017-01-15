package com.walkerchristie.content;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		boolean running = true;
		Scanner scanner = new Scanner(System.in);
		RequestProcessor processor = new RequestProcessor();
		System.out.println(processor.respondTo(scanner.nextLine()));
		
		while (running) {
			processor.respondTo(scanner.nextLine());
		}

		scanner.close();
	}
}
