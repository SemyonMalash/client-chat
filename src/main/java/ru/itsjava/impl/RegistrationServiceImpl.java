package ru.itsjava.impl;

import lombok.SneakyThrows;
import ru.itsjava.services.MessageInputService;
import ru.itsjava.services.RegistrationService;

import java.io.PrintWriter;
import java.net.Socket;

import static ru.itsjava.impl.ClientServiceImpl.HOST;
import static ru.itsjava.impl.ClientServiceImpl.PORT;

public class RegistrationServiceImpl implements RegistrationService {

    @SneakyThrows
    @Override
    public void registrate() {
        Socket socket = new Socket(HOST, PORT);
        if (socket.isConnected()) {
            new Thread(new SocketRunnable(socket)).start();

            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            MessageInputService messageInputService = new MessageInputServiceImpl(System.in);

            System.out.println("Придумайте логин:");
            String login = messageInputService.getMessage();

            System.out.println("Придумайте пароль:");
            String password = messageInputService.getMessage();

            serverWriter.println("!registr!" + login + ":" + password);
            serverWriter.flush();

            System.out.println("Вы успешно зарегистрированы");

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

