package ninja.tilman.chef;

import java.util.Scanner;

import ninja.tilman.chef.cli.CommandProcessor;

public class Main {
	public static void main(String[] args) {
		CommandProcessor processor = new CommandProcessor();
		
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("> ");
			while (scanner.hasNextLine()) {
				String commandResult = processor.processCommand(scanner.nextLine());
				System.out.println(commandResult);
				System.out.print("> ");
			}
		}
	}
}
