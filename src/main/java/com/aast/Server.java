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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 5533;
    private ServerSocket serverSocket;
    private ClientListener listener;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public boolean startListening() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Socket socket = serverSocket.accept();
                        Client client = new Client(socket);
                        System.out.println("Client connected: "+ client);
                        if(listener != null) {
                            listener.newClient(client);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return true;
    }

    public void setListener(ClientListener listener) {
        this.listener = listener;
    }

    public ClientListener getListener() {
        return listener;
    }

    public static void main(String[] args) throws IOException {
        String enc = EncryptManager.encryptAES("testtesttesttestt", "data");
        System.out.println(enc);
        System.out.println(EncryptManager.decryptAES("testtesttesttestt", enc));
        Server server = new Server();
        System.out.println("Starting listening..");
        server.startListening();
        System.out.println("Done");
    }
}
