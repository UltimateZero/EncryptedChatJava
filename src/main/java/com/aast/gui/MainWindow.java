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
package com.aast.gui;

import com.aast.Client;
import com.aast.ClientListener;
import com.aast.MessageListener;
import com.aast.Server;
import com.aast.encrypt.EncryptManager;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by UltimateZero on 5/6/2017.
 */
public class MainWindow {
    private JTextField txtIp;
    private JTextField txtPort;
    private JList<String> listMessages;
    private JList<String> listRaw;
    private JTextField txtSend;
    private JTextField txtKey;
    private JButton btnConnect;
    public JPanel mainView;
    private JTextField txtListenPort;
    private JButton startListeningButton;

    private DefaultListModel<String> modelRaw;
    private DefaultListModel<String> modelMessages;

    private Server server;
    private Client currentClient;

    public MainWindow() {

        modelRaw = new DefaultListModel<>();
        modelMessages = new DefaultListModel<>();

        listRaw.setModel(modelRaw);
        listMessages.setModel(modelMessages);

        startListeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startListeningButton.setEnabled(false);
                String key = txtKey.getText();
                EncryptManager.setKey(key);
                int port = Integer.parseInt(txtListenPort.getText());
                try {
                    server = new Server(port);
                    server.setListener(new ClientListener() {
                        @Override
                        public void newClient(Client client) {
                            currentClient = client;
                            newClientReceived();
                        }
                    });
                    server.startListening();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked");
                String ip = txtIp.getText();
                int port = Integer.parseInt(txtPort.getText());
                if(currentClient != null) {
                    currentClient.disconnect();
                    currentClient = null;
                }
                currentClient = new Client(ip, port);
                String key = txtKey.getText();
                EncryptManager.setKey(key);
                btnConnect.setEnabled(false);
                currentClient.connect();
                newClientReceived();
            }
        });
        txtSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("event");
                String message = txtSend.getText();
                String key = txtKey.getText();
                EncryptManager.setKey(key);
                try {
                    currentClient.sendMessage(message);
                    addSelfMessage(message);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                txtSend.setText("");

            }
        });
    }

    private void addSelfMessage(String message) {
        modelRaw.addElement("Me: " + EncryptManager.encryptAES(EncryptManager.key, message));
        modelMessages.addElement("Me: " + message);
    }

    private void newClientReceived() {
        addMessage("Client connected", true);
        currentClient.setListener(new MessageListener() {
            @Override
            public void messageReceived(String message) {
                if(message == null)
                    modelMessages.addElement("Other: Invalid key");
                else
                    modelMessages.addElement("Other: " + message);
            }

            @Override
            public void rawMessageReceived(String message) {
                modelRaw.addElement("Other: " + message);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    currentClient.startReceiving();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void addMessage(String message, boolean both) {
        modelRaw.addElement(message);
        if(both)
            modelMessages.addElement(message);
    }
}
