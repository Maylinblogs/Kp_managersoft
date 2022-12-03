package org.menegment;

import org.menegment.utility.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int PORT_SERVER = 8046;
    private static ServerSocket serverSocket;
    private static ClientHandler clientHandler;
    private static Thread thread;
    private static List<Socket> clientsSockets = new ArrayList<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT_SERVER);
            while (true) {
                for (Socket socket : clientsSockets) {
                    if (socket.isClosed()) {
                        clientsSockets.remove(socket);
                    }
                }
                Socket socket = serverSocket.accept();
                clientsSockets.add(socket);
                clientHandler = new ClientHandler(socket);
                thread = new Thread(clientHandler);
                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Проблеммы с стартом сервера!!!");
        }
    }
}