package com.client.utility;

import lombok.Data;
import org.menegment.models.User;
import org.menegment.models.Worker;

import java.io.*;
import java.net.Socket;


@Data
public class ClientSocket {
    private final static int PORT = 8046;
    private final static String address = "127.0.0.1";
    private Socket clientSocket;

    private static User localUser;
    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;
    private static ClientSocket instance;


    public static void setLocalUser(User user) {
        localUser = user;
    }

    public static User getLocalUser() {
        return localUser;
    }

    private ClientSocket() throws IOException {
        this.clientSocket = new Socket(address, PORT);
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public static ClientSocket getInstance() {
        if (instance == null) {
            try {
                instance = new ClientSocket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}
