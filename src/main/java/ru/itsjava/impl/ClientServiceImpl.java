package ru.itsjava.impl;

import lombok.SneakyThrows;
import ru.itsjava.services.ClientService;
import ru.itsjava.services.MessageInputService;

public class ClientServiceImpl implements ClientService {
    public static final int PORT = 8081;
    public static final String HOST = "localhost";

    @SneakyThrows
    @Override
    public void start() {
        MessageInputService messageInputService = new MessageInputServiceImpl(System.in);

        System.out.println("Нажмите 1, чтобы авторизоваться");
        System.out.println("Нажмите 2, чтобы зарегистрироваться");

        String num = messageInputService.getMessage();

        switch (num) {
            case "1":
                new AuthorizationServiceImpl().authorize();
            case "2":
                new RegistrationServiceImpl().registrate();
            default:
                new AuthorizationServiceImpl().authorize();
        }
    }
}


