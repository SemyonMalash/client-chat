package ru.itsjava.services.impl;

import lombok.SneakyThrows;
import ru.itsjava.services.AuthorizationService;
import ru.itsjava.services.MessageInputService;

import java.io.PrintWriter;
import java.net.Socket;

import static ru.itsjava.services.impl.ClientServiceImpl.HOST;
import static ru.itsjava.services.impl.ClientServiceImpl.PORT;

public class AuthorizationServiceImpl implements AuthorizationService {

    @SneakyThrows
    @Override
    public void authorize() {
        Socket socket = new Socket(HOST, PORT);
        if (socket.isConnected()) {
            new Thread(new SocketRunnable(socket)).start();

            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            MessageInputService messageInputService = new MessageInputServiceImpl(System.in);

            System.out.println("Введите свой логин:");
            String login = messageInputService.getMessage();

            System.out.println("Введите свой пароль:");
            String password = messageInputService.getMessage();

            //!autho!login:password
            serverWriter.println("!autho!" + login + ":" + password);
            serverWriter.flush();

            System.out.println("Вы успешно авторизованы");

            while (true) {
                String consoleMessage = messageInputService.getMessage();
                if (consoleMessage.equals("Exit")) {
                    socket.close();
                    System.exit(-1);
                }
                serverWriter.println(consoleMessage);
                serverWriter.flush();
            }
        }
    }
}
