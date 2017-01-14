import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		RequestProcessor processor = new RequestProcessor("pi");
		System.out.println(processor.getResponse());
		while (true) {
			String input = scanner.nextLine();
			if (processor.validRequest(input)) {
				System.out.println(processor.podString(Integer.valueOf(input)));
			} else {
				processor =  new RequestProcessor(input);
				System.out.println(processor.getResponse());
			}
		}
	}
}
