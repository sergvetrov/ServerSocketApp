package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Main {
    private final static int SERVER_PORT = 8081;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is listening on port: " + SERVER_PORT);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "CP1251"));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                out.println("Вас вітає сервер!");
                String usrInput = in.readLine().toLowerCase();
                if (usrInput.contains("ы") ||
                        usrInput.contains("ё") ||
                        usrInput.contains("ъ") ||
                        usrInput.contains("э")) {

                    out.println("Що таке паляниця?");
                    if (!in.readLine().equalsIgnoreCase("хліб")) {
                        clientSocket.close();
                    }
                }
                out.println(LocalDateTime.now());
                out.println("На все добре!");
            } catch (IOException e) {
                System.out.println("cannot to create I/O stream");
            }
            clientSocket.close();
            System.out.println("Client connection closed");
        } catch (IOException e) {
            System.out.println("cannot to open socket");
        }
    }
}