package ru.itsjava.services.impl;

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
        System.out.println("Нажмите любую кнопку, чтобы зарегистрировться");

        if (messageInputService.getMessage().equals("1")) {
            new AuthorizationServiceImpl().authorize();
        }
        new RegistrationServiceImpl().registrate();
    }
}


