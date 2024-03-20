package chatclient;

import java.net.Socket;

public class Client {

    public Client() {

    }

    public void ClientStart(String host, int port) {
        try {

            Socket client = new Socket(host, port);
            ClientGiaoDien clientGiaoDien = new ClientGiaoDien();
            ClientThread th = new ClientThread(client, clientGiaoDien);
            th.start();

            clientGiaoDien.nhanThread(th);
            clientGiaoDien.khoiDong();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void khoiDong() {
        try {
            ClientStart("localhost", 6665);
        } catch (Exception e) {

        }
    }
}
