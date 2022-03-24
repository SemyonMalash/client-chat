package ru.itsjava.services;

import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientServiceImpl implements ClientService {
    public static final int PORT = 8081;
    public static final String HOST = "localhost";

    @SneakyThrows
    @Override
    public void start() {
        Socket socket = new Socket(HOST, PORT);
        if (socket.isConnected()) {
            Thread thread = new Thread(new SocketRunnable(socket));
            thread.start();

            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            MessageInputService messageInputService = new MessageInputServiceImpl(System.in);

            System.out.println("Введите свой логин");
            String login = messageInputService.getMessage();

            System.out.println("Введите свой пароль:");
            String password = messageInputService.getMessage();

            //!autho!login:password
            serverWriter.println("!autho!" + login + ":" + password);
            serverWriter.flush();

            while (true) {
                String consoleMessage = messageInputService.getMessage();
                if (consoleMessage.equals("Exit")) {
                    socket.close();
                    System.exit(-1);

                    serverWriter.println(consoleMessage);
                    serverWriter.flush();
                }
            }
        }
    }
}
