package com.geekbreins.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int CLINT_PORT = 8189;
    private static final String CLINT_ADDR = "localhost";

    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static Scanner inputMsg;


    public static void main(String[] args){

        Thread toReadUserMsg = new Thread(()->{
            inputMsg = new Scanner(System.in);
                while(true){
                    try {
                        out.writeUTF( inputMsg.nextLine());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            });
        toReadUserMsg.start();


        try {
            socket = new Socket( CLINT_ADDR,CLINT_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread dataThread = new Thread(()->{
                try {
                    while(true){
                        String msg = in.readUTF();
                        System.out.println(msg);
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });

            dataThread.start();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
