/**
 *
 * Copyright 20XX the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aast;

import com.aast.encrypt.EncryptManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by UltimateZero on 5/6/2017.
 */
public class Client {
    private String ip;
    private int port;
    private Socket socket;
    private String buffer;
    private MessageListener listener;


    public Client(Socket socket) {
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        this.port = socket.getPort();
        buffer = "";
    }

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        socket = new Socket();
        buffer = "";
    }

    public void connect() {
        try {
            socket.connect(new InetSocketAddress(ip, port));
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

    public MessageListener getListener(){
        return listener;
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void disconnect() {
        if(isConnected())
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void sendMessage(String message) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.print(EncryptManager.encryptAES(EncryptManager.key, message) + "\0");
        writer.flush();
    }

    public void startReceiving() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        char c;
//		boolean done = false;
        while(true) {
            while(reader.ready()) {
                c = (char)reader.read();
                if(c == '\0') {
                    System.out.println("Done receiving");
                    String decrypted = EncryptManager.decryptAES(EncryptManager.key, buffer);
                    if(listener != null) {
                        listener.rawMessageReceived(buffer);
                        listener.messageReceived(decrypted);
                    }
                    System.out.println(decrypted);
                    buffer = "";
//					done = true;
                    break;
                }
                buffer += c;
                System.out.println(buffer);

            }
//			if(done) break;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return ip + ":" + port;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 5533);
        System.out.println("Starting to connect to server");
        client.connect();
        client.startReceiving();
        System.out.println("Done");
    }
}
