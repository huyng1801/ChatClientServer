package chatserver;

import java.sql.Statement;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerThread implements Runnable {

    private Socket socket;
    //private String data;
    private DataInputStream in;
    private DataOutputStream out;
    private String listUser;
    //private String userNguoiDungMuonKetNoi;
    private HashMap<String, Socket> threadMap;
    //private String tinNhan;
    //private String tinNhanNhan;
    //private String tinNhanGui;
    private String userName;
    private String passWord;
    //private String user;
    //private String pass;
    private String trangThai;
    //private String tenNguoiDungMuonChat;
    private String tinnhan;
    private String userGui;
    private String userNhan;
    private ServerGiaoDien serverGiaoDien;

    public ServerThread(Socket socket, HashMap<String, Socket> threadMap, ServerGiaoDien serverGiaoDien) {
        this.socket = socket;
        this.threadMap = threadMap;
        this.serverGiaoDien = serverGiaoDien;
    }

    public String hienThiDanhSachNguoiDung() {
        int i = 1;
        listUser = "";
        for (Entry<String, Socket> entry : threadMap.entrySet()) {
            String key = entry.getKey();
            listUser = listUser + i + ": " + key + "\n";
            i++;
        }
        serverGiaoDien.soNguoiDungOnline(threadMap.size());
        System.out.println("Co " + threadMap.size() + " dang online.\n" + listUser);
        return ("Co " + threadMap.size() + " dang online.\n" + listUser);
    }

    public void truyenNguoiDung() {
        for (Entry<String, Socket> entry : threadMap.entrySet()) {
            String key = entry.getKey();
            String nguoidung = "themNguoiDungVaoList " + key;
            serverGiaoDien.addListNguoiDungOnline(key);
            guiTinNhanChoTatCaNguoiDung(nguoidung);
        }
    }

    public void guiTinNhanChoTatCaNguoiDung(String tinNhan) {
        for (Entry<String, Socket> entry : threadMap.entrySet()) {
            Socket targetThread = threadMap.get(entry.getKey());
            try {
                System.out.println(targetThread.getRemoteSocketAddress());
                DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
                out1.writeUTF(tinNhan);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void guiTinNhanChoNguoiDung(String tinNhan, String user) {
        Socket targetThread = threadMap.get(user);
        try {
            System.out.println(targetThread.getRemoteSocketAddress());
            DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
            out1.writeUTF(userName + ": " + tinNhan);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void xoaNguoiDungRaKhoiHash() {
        threadMap.remove(userName);
        for (Entry<String, Socket> entry : threadMap.entrySet()) {
            String key = entry.getKey();
            String nguoidung = "xoaNguoiDungKhoiList " + userName;
            guiTinNhanChoTatCaNguoiDung(nguoidung);
        }
    }

    public void truyenNguoiDungDaKetNoi() {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT DISTINCT ten_nguoi_nhan FROM tin_nhan WHERE ten_nguoi_gui = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String user = rs.getString("ten_nguoi_nhan");
                try {
                    out.writeUTF("themNguoiDungcu List " + user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xuLyDangNhap() {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM nguoi_dung WHERE ten_nguoi_dung = ? AND mat_khau = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passWord);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String user = rs.getString("ten_nguoi_dung");
                String pass = rs.getString("mat_khau");
                if (user.equals(userName) && pass.equals(passWord)) {
                    out.writeUTF("thanhcong");
                } else {
                    out.writeUTF("khongthanhcong");
                }
            } else {
                out.writeUTF("khongthanhcong");
            }
            rs.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            try {
                out.writeUTF("khongthanhcong");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void xuLyDangKi() {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO nguoi_dung (ten_nguoi_dung, mat_khau) VALUES (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passWord);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try {
                    out.writeUTF("thanhcong");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    out.writeUTF("khongthanhcong");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            preparedStatement.close();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                out.writeUTF("khongthanhcong");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void taoNhom() {
        try {
            String groupName = in.readUTF();
            if (isGroupNameExist(groupName)) {
            } else {
                if (addGroupToDatabase(groupName)) {
                } else {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isGroupNameExist(String groupName) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT ten_nhom FROM nhom WHERE ten_nhom = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, groupName);
            boolean groupNameExists = false;
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                groupNameExists = true;
            }
            rs.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);

            return groupNameExists;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    private boolean addGroupToDatabase(String groupName) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO nhom (ten_nhom) VALUES (?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, groupName);
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void xuLyTinNhan(String tinNhan, String userName1, String userName2) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO tin_nhan (noi_dung_tin_nhan, ten_nguoi_gui, ten_nguoi_nhan) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, tinNhan);
            preparedStatement.setString(2, userName1);
            preparedStatement.setString(3, userName2);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkNguoiDungTrongNhom(String userName, String groupName) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT ma_nhom_nguoi_dung FROM nhom_nguoi_dung WHERE ten_nguoi_dung = ? and ten_nhom = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, groupName);
            boolean userExists = false;
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                userExists = true;
            }
            rs.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);
            return userExists;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void themNguoiDungVaoNhom(String userName, String groupName) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO nhom_nguoi_dung (ten_nguoi_dung, ten_nhom) VALUES (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, groupName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xuLyTinNhanNhom(String tinNhan, String userName, String groupName) {
        boolean check = checkNguoiDungTrongNhom(userName, groupName);
        if (!check) {
            themNguoiDungVaoNhom(userName, groupName);
        }
        try {
            System.out.println(groupName);
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO tin_nhan_nhom (noi_dung_tin_nhan_nhom, ten_nguoi_dung, ten_nhom) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, tinNhan);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, groupName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<String> layDanhSachNguoiDungTrongNhom(String groupName) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT ten_nguoi_dung FROM nhom_nguoi_dung where ten_nhom = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, groupName);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> userList = new ArrayList<>();
            while (rs.next()) {
                String user = rs.getString("ten_nguoi_dung");
                userList.add(user);
            }

            rs.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);
            return userList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void truyenTinNhanNhomTuCsdlVeClient(String userGui, String groupName) {
        boolean check = checkNguoiDungTrongNhom(userName, groupName);
        if (!check) {
            themNguoiDungVaoNhom(userName, groupName);
        }
        List<String> dsNguoiDung = layDanhSachNguoiDungTrongNhom(groupName);
        System.out.println(dsNguoiDung.size());
        for (String user : dsNguoiDung) {
            if (threadMap.containsKey(user)) {
                try {
                    Connection con = JDBCUtil.getConnection();
                    String sql = "SELECT * FROM tin_nhan_nhom where ten_nhom = ? order by thoi_gian desc";
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, groupName);
                    ResultSet rs = preparedStatement.executeQuery();

                    try {
                        Socket targetThread = threadMap.get(user);
                        DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
                        out1.writeUTF("resetMess00000000000-" + user + "-" + groupName);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    while (rs.next()) {
                        String time = rs.getString("thoi_gian");
                        String message = rs.getString("noi_dung_tin_nhan_nhom");
                        String sender = rs.getString("ten_nguoi_dung");

                        try {
                            Socket targetThread = threadMap.get(user);
                            DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());

                            out1.writeUTF("GroupMessage0000Time[" + time + "] TinNhan[" + message + "] UserName[" + sender + "] GroupName[" + groupName + "]");
                        } catch (Exception e) {
                        }

                    }
                    JDBCUtil.closeConnection(con);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void truyenTinNhanTuCsdlVeClient(String userGui, String userNhan) {

        if (threadMap.containsKey(userNhan)) {
            try {
                Connection con = JDBCUtil.getConnection();
                Statement st = con.createStatement();
                String sql = "SELECT * FROM tin_nhan WHERE ten_nguoi_gui ='" + userGui + "' AND ten_nguoi_nhan = '"
                        + userNhan + "'OR ten_nguoi_gui = '" + userNhan + "' AND ten_nguoi_nhan = '" + userGui
                        + "'ORDER BY thoi_gian DESC;";
                ResultSet rs = st.executeQuery(sql);
                try {
                    Socket targetThread = threadMap.get(userNhan);
                    DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
                    out1.writeUTF("resetMess00000000000-" + userNhan + "-" + userGui);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                while (rs.next()) {
                    String time = rs.getString("thoi_gian");
                    String tinNhan = rs.getString("noi_dung_tin_nhan");
                    String UserName1 = rs.getString("ten_nguoi_gui");
                    String UserName2 = rs.getString("ten_nguoi_nhan");
                    try {
                        out.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1 + "]"
                                + " user2[" + UserName2 + "]");
                        try {
                            Socket targetThread = threadMap.get(userNhan);
                            System.out.println(targetThread.getRemoteSocketAddress());
                            DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
                            out1.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1 + "]"
                                    + " user2[" + UserName2 + "]done");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                    }
                }
                JDBCUtil.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Connection con = JDBCUtil.getConnection();
                Statement st = con.createStatement();
                String sql = "SELECT * FROM tin_nhan WHERE ten_nguoi_gui ='" + userGui + "' AND ten_nguoi_nhan = '"
                        + userNhan + "'OR ten_nguoi_gui = '" + userNhan + "' AND ten_nguoi_nhan = '" + userGui
                        + "'ORDER BY thoi_gian DESC;";
                System.out.println(sql);
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    String time = rs.getString("thoi_gian");
                    String tinNhan = rs.getString("noi_dung_tin_nhan");
                    String UserName1 = rs.getString("ten_nguoi_gui");
                    String UserName2 = rs.getString("ten_nguoi_nhan");
                    try {
                        out.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1 + "]"
                                + " user2[" + UserName2 + "]");
                    } catch (Exception e) {
                    }
                }
                JDBCUtil.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveFile(String sender, String receiver, String fileName, String fileExtension, long size) {
        try {
            System.out.println("Received File Information:");
            System.out.println("Sender: " + sender);
            System.out.println("Receiver: " + receiver);
            System.out.println("File Name: " + fileName + "." + fileExtension);
            if (size == -1) {
                System.err.println("File not found on the client.");
                return;
            }

            String savePath = "D:/";
            File file = new File(savePath + File.separator + fileName + "." + fileExtension);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytesRead = 0;
            System.out.println("File Size: " + size + " bytes");
            while (totalBytesRead < size) {
                bytesRead = in.read(buffer);
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            fos.close();
            System.out.println("File received successfully from " + sender + " to " + receiver + ": " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendGroupListToClient() {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT ten_nhom FROM nhom";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> groupList = new ArrayList<>();
            while (rs.next()) {
                String groupName = rs.getString("ten_nhom");
                groupList.add(groupName);
            }

            rs.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(con);
            String groupNames = String.join(",", groupList);
            out.writeUTF("groupList:" + groupNames);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            // nhan ve trang thai(dangnhap, dangki, guiTinNhan)
            trangThai = "";
            while (!trangThai.equals("thoat")) {
                trangThai = in.readUTF();
                System.out.println(trangThai);

                synchronized (in) {
                    if (trangThai.equals("dangNhap")) {
                        try {

                            userName = in.readUTF();
                            passWord = in.readUTF();
                            System.out.println("xu ly dang nhap");
                            System.out.println(userName + passWord);
                            xuLyDangNhap();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (trangThai.equals("dangKi")) {
                        try {
                            userName = in.readUTF();
                            passWord = in.readUTF();
                            System.out.println("xu ly dang ki");
                            System.out.println(userName + passWord);
                            xuLyDangKi();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (trangThai.equals("nhanThongTin")) {

                        userName = in.readUTF();
                        System.out.println(userName);
                        threadMap.put(userName, socket);

                        // truyen danh sach nguoi dung toi client
                        hienThiDanhSachNguoiDung();
                        truyenNguoiDungDaKetNoi();
                        truyenNguoiDung();

                    } else if (trangThai.equals("guiTinNhan")) {
                        synchronized (in) {
                            tinnhan = in.readUTF();
                            userGui = in.readUTF();
                            userNhan = in.readUTF();
                        }
                        out.writeUTF("resetMess00000000000-" + userGui + "-" + userNhan);
                        xuLyTinNhan(tinnhan, userGui, userNhan);
                        truyenNguoiDungDaKetNoi();
                        truyenTinNhanTuCsdlVeClient(userGui, userNhan);

                    } else if (trangThai.equals("layTinNhanTuCSDL")) {
                        userGui = in.readUTF();
                        userNhan = in.readUTF();
                        truyenNguoiDungDaKetNoi();
                        out.writeUTF("resetMess00000000000-" + userGui + "-" + userNhan);
                        truyenTinNhanTuCsdlVeClient(userGui, userNhan);

                    } else if (trangThai.equals("createGroupChat")) {
                        taoNhom();
                    } else if (trangThai.equals("getGroupList")) {
                        sendGroupListToClient();
                    } else if (trangThai.equals("layTinNhanNhomTuCSDL")) {
                        String userName = in.readUTF();
                        String groupName = in.readUTF();
                        truyenNguoiDungDaKetNoi();
                        out.writeUTF("resetMess00000000000-" + userName + "-" + groupName);
                        truyenTinNhanNhomTuCsdlVeClient(userName, groupName);

                    } else if (trangThai.equals("guiTinNhanNhom")) {
                        synchronized (in) {
                            tinnhan = in.readUTF();
                            userGui = in.readUTF();
                            userNhan = in.readUTF();
                        }

                        out.writeUTF("resetMess00000000000-" + userName + "-" + userNhan);
                        xuLyTinNhanNhom(tinnhan, userGui, userNhan);
                        truyenNguoiDungDaKetNoi();
                        truyenTinNhanNhomTuCsdlVeClient(userGui, userNhan);

                    } else if (trangThai.equals("sendFile")) {
                        String sender = in.readUTF();
                        String receiver = in.readUTF();
                        String fileName = in.readUTF();
                        String fileExtension = in.readUTF();
                        long size = in.readLong();
                        if (isGroupNameExist(receiver.replace("#", ""))) {
                            receiveFile(sender, receiver, fileName, fileExtension, size);
                            out.writeUTF("resetMess00000000000-" + sender + "-" + receiver.replace("#", ""));
                            xuLyTinNhanNhom("file:" + fileName + "." + fileExtension, sender, receiver.replace("#", ""));
                            truyenNguoiDungDaKetNoi();
                            truyenTinNhanNhomTuCsdlVeClient(sender,  receiver.replace("#", ""));
                            
                        } else {
                            receiveFile(sender, receiver, fileName, fileExtension, size);
                             out.writeUTF("resetMess00000000000-" + sender + "-" + receiver);
                            xuLyTinNhan("file:" + fileName + "." + fileExtension, sender, receiver);
                            
       
                            truyenNguoiDungDaKetNoi();
                            truyenTinNhanTuCsdlVeClient(sender, receiver);

                            
                        }

                    } else if (trangThai.equals("downloadFile")) {
                        try {
                            String fileName = in.readUTF();
                            File fileToSend = new File("D:\\" + fileName);
                            long fileSizeInBytes = fileToSend.length();
                            if (fileToSend.exists() && !fileToSend.isDirectory()) {
                                out.writeUTF("fileExists");
                                out.writeUTF(fileName);
                                out.writeLong(fileSizeInBytes);
                                FileInputStream fis = new FileInputStream(fileToSend);
                                BufferedInputStream bis = new BufferedInputStream(fis);
                                byte[] buffer = new byte[4096];
                                int bytesRead;

                                while ((bytesRead = bis.read(buffer)) != -1) {
                                    System.out.println(bytesRead);
                                    out.write(buffer, 0, bytesRead);
                                }
                                bis.close();
                                fis.close();

                                System.out.println("File sent to client: " + fileName);
                            } else {
                                out.writeUTF("fileNotFound");
                                System.out.println("File not found on server: " + fileName);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

        } catch (IOException e) {
            xoaNguoiDungRaKhoiHash();
            serverGiaoDien.removeList(userName);
            hienThiDanhSachNguoiDung();

        }

    }

}
