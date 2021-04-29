package com.geekbreins.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {

    private static final int SERVER_PORT = 8189;
    private static Socket socket = null;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static Scanner inputMsg;

    public static void main(String[] args) {


        Thread toReadUserMsg = new Thread(()->{
            inputMsg = new Scanner(System.in);
            while(true) {
                try {
                    writeUTFUser("Сщщбщение с консоли Sever = "+ inputMsg.nextLine());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        toReadUserMsg.start();
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String str = in.readUTF();
                writeUTFUser("Эхо = "+str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static synchronized void writeUTFUser(String msg) throws IOException {
        out.writeUTF(msg);
     }
}
