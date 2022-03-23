package sap.gb.cloud;

import sap.gb.cloud.command.Command;
import sap.gb.cloud.command.CommandResolver;
import sap.gb.cloud.command.RequestBuilder;
import sap.gb.cloud.handler.Handler;
import sap.gb.cloud.net.Connector;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final int PORT = 8993;
    private String serverIp;
    private Scanner console;
    private boolean isAuth;

    public Client() {
        this.serverIp = "localhost";
        this.console = new Scanner(System.in);
    }

    public void run() {
        String userInput;
        Command command;
        RequestBuilder requestBuilder;
        CommandResolver commandResolver = new CommandResolver();
        Connector connector = new Connector();
        Handler handler = new Handler();
        Socket connection = connector.getConnection(serverIp, PORT);
        while (!isAuth) {
            System.out.println("enter login");
            String login = console.nextLine();
            System.out.println("enter password");
            String password = console.nextLine();
            String[] authPreform = {login, password};
            isAuth = handler.handle(connection, null, new RequestBuilder(authPreform));
        }
                try {
                    while (true) {
                        userInput = console.nextLine();
                        if (userInput.isEmpty()) {
                            continue;
                        }
                        if (userInput.equals("help")) {
                            for (Command c : Command.values())
                                System.out.println(c.getDescription());
                            continue;
                        }
                        requestBuilder = commandResolver.resolve(userInput);
                        if (requestBuilder == null) {
                            System.out.println("There is no such command, type 'help' for help");
                            continue;
                        }
                        command = commandResolver.getCommand();
                        handler.handle(connection, command, requestBuilder);
                        if (command == Command.EXIT)
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.shutdownInput();
                        connection.shutdownOutput();
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
    }

    public static void main(String[] args) throws Exception {
        new Client().run();
    }
}
