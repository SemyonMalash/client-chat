package ru.itsjava;

import ru.itsjava.services.ClientServiceImpl;

public class Main {

    public static void main(String[] args) {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.start();
    }
}