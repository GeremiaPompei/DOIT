package it.unicam.cs.ids.DOIT.view;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class MainView {
    private Scanner scanner;
    private Map<String, Map<String, Function<String[], String>>> commands;

    public MainView(Map<String, Map<String, Function<String[], String>>> commands) {
        this.scanner = new Scanner(System.in);
        this.commands = commands;
    }

    public void start() {
        String command1 = "";
        String command2 = "";
        System.out.println("***DOIT***");
        while (!command1.equalsIgnoreCase("exit")) {
            System.out.println("Write \"exit\" to leave app!");
            System.out.println("Options: " + this.commands.keySet());
            System.out.print(" > ");
            command1 = this.scanner.nextLine();
            if (this.commands.keySet().contains(command1)) {
                while (!command2.equalsIgnoreCase("back")) {
                    System.out.println("Write \"back\" to come back.");
                    System.out.println("Options: " + this.commands.get(command1).keySet());
                    System.out.print("   > ");
                    command2 = this.scanner.nextLine();
                    String[] params = command2.split(" ");
                    if (this.commands.get(command1).keySet().contains(params[0])) {
                        String result =
                                this.commands.get(command1).get(params[0]).apply(params);
                        System.out.println("\n" + result + "\n");
                    }
                }
                command2 = "";
            }
        }
        System.out.println("Goodbye...");
    }

    public void stop() {
        this.scanner.close();
    }
}
