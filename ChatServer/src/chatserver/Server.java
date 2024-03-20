package chatserver;

import java.net.*;
import java.util.HashMap;

public class Server {

    private HashMap threadMap;

    public void ServerStart(int port) {
        try {
            ServerGiaoDien serverGiaoDien = new ServerGiaoDien();
            serverGiaoDien.khoiDong();
            //String publicIPAddress = InetAddress.getLocalHost().getHostAddress();
            String publicIPAddress = "localhost";
            ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(publicIPAddress));

            System.out.println("Server đã khởi động. Địa chỉ IP public: " + publicIPAddress + ", Port: " + port);
            threadMap = new HashMap<>();
            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("Client " + socket.getInetAddress().getHostName() + " da ket noi");

                //taoThread
                ServerThread threadServer = new ServerThread(socket, threadMap, serverGiaoDien);
                Thread thread = new Thread(threadServer);
                thread.start();

            }

        } catch (Exception e) {

        }
    }

    public void khoiDong() {

        Server server = new Server();
        try {
            server.ServerStart(6665);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
