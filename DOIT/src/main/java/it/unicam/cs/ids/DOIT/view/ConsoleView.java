package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.user.IUserHandler;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class ConsoleView implements IView {
    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void start(IUserHandler controller) {
        Map<String, Map<String, Function<String[], String>>> commands;
        commands = new ControllerView(controller).getCommands();
        String command1 = "";
        String command2 = "";
        System.out.println("***DOIT***");
        while (!command1.equalsIgnoreCase("exit")) {
            System.out.println(" Write \"exit\" to leave app!");
            System.out.println(" Options: " + commands.keySet());
            System.out.print(" > ");
            command1 = this.scanner.nextLine();
            if (commands.keySet().contains(command1)) {
                while (!command2.equalsIgnoreCase("back")) {
                    System.out.println("    Write \"back\" to come back!");
                    System.out.println("    Options: " + commands.get(command1).keySet());
                    System.out.print("   > ");
                    command2 = this.scanner.nextLine();
                    String[] params = command2.split(" ");
                    if (commands.get(command1).keySet().contains(params[0])) {
                        String result = commands.get(command1).get(params[0]).apply(params);
                        System.out.println("\n" + result + "\n");
                    } else if (!command2.equalsIgnoreCase("back"))
                        System.out.println("\n Command not found!\n");
                }
                command2 = "";
            } else if (!command1.equalsIgnoreCase("exit"))
                System.out.println("\n Command not found!\n");
        }
        System.out.println("Goodbye...");
    }

    public void stop() {
        this.scanner.close();
    }
}
