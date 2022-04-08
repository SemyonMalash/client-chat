package ru.itsjava.impl;

import lombok.SneakyThrows;
import ru.itsjava.services.AuthorizationService;
import ru.itsjava.services.MessageInputService;
import ru.itsjava.services.ReauthorizationService;

import java.io.PrintWriter;
import java.net.Socket;

public class AuthorizationServiceImpl implements AuthorizationService, ReauthorizationService {
    String login;
    String password;
    Socket socket;

    @SneakyThrows
    @Override
    public void authorize() {
        socket = new Socket(ClientServiceImpl.HOST, ClientServiceImpl.PORT);

        if (socket.isConnected()) {
            new Thread(new SocketRunnable(socket)).start();
            /* Я не знаю, в какой момент пропала .start(), потратил кучу времени,
            чтобы разобраться, почему не приходят сообщения :)
            Писал раньше Thread thread = new Thread; thread.start(); и в какой-то момент убрал, и не заметил,
            всё-таки тесты нужны, чтобы избегать таких ошибок) */
            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            MessageInputService messageInputService = new MessageInputServiceImpl(System.in);

            System.out.println("Введите свой логин:");
            login = messageInputService.getMessage();

            System.out.println("Введите свой пароль:");
            password = messageInputService.getMessage();

            serverWriter.println("!autho!" + login + ":" + password);
            serverWriter.flush();

            System.out.println("Вы успешно авторизованы");

            while (true) {
                String consoleMessage = messageInputService.getMessage();

//                if (consoleMessage.equals("Exit")) {
//                    socket.close();
//                    System.exit(-1);
//                }
//                if (consoleMessage.equals("Login")) {
//                    reauthorize();
//                }
                serverWriter.println(consoleMessage);
                serverWriter.flush();
            }
        }
    }

    @SneakyThrows
    @Override
    public void reauthorize() {
        socket = new Socket(ClientServiceImpl.HOST, ClientServiceImpl.PORT);

        if (socket.isConnected()) {
            new Thread(new SocketRunnable(socket)).start();

            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            MessageInputService messageInputService = new MessageInputServiceImpl(System.in);

            System.out.println("Введите свой логин:");
            login = messageInputService.getMessage();

            System.out.println("Введите свой пароль:");
            password = messageInputService.getMessage();

            serverWriter.println("!reautho!" + login + ":" + password);
            serverWriter.flush();

            System.out.println("Вы успешно авторизованы");
        }
    }
}
