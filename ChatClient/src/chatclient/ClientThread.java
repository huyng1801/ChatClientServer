package chatclient;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

public class ClientThread extends Thread {

    private Socket client;
    private DataOutputStream out;
    private String userName;
    private DataInputStream in;
    private String tinNhanNhan;
    private String userNhan;
    private ClientGiaoDien clientGiaoDien;

    public ClientThread(Socket socket, ClientGiaoDien clientGiaoDien) {

        this.client = socket;
        this.clientGiaoDien = clientGiaoDien;

    }

    public void xuLyDangNhap(String userName, String passWord) {

        this.userName = userName;
        try {
            out.writeUTF("dangNhap");
            out.writeUTF(userName);
            out.writeUTF(passWord);
            String trangThaiDangNhap = in.readUTF();
            System.out.println(trangThaiDangNhap.equals("thanhcong"));
            if (trangThaiDangNhap.equals("thanhcong")) {
                clientGiaoDien.trangNguoiDung();
                clientGiaoDien.thongBaoDangNhapThanhCong();
                xuLyServer();
                sendGetGroupListMessage();

            } else {

                clientGiaoDien.thongBaoDangNhapKhongThanhCong();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void xuLyDangKy(String userName, String passWord) {
        try {
            out.writeUTF("dangKi");
            out.writeUTF(userName);
            out.writeUTF(passWord);
            // tra ve trang thai dang nhap

            String trangThaiDangNhap = in.readUTF();
            System.out.println(trangThaiDangNhap.equals("thanhcong"));
            if (trangThaiDangNhap.equals("thanhcong")) {

                clientGiaoDien.thongBaoDangKiThanhCong();

            } else {

                clientGiaoDien.thongBaoDangKiKhongThanhCong();
            }
        } catch (Exception e) {

        }

    }

    public void xuLyServer() {
        try {
            System.out.println(userName);
            out.writeUTF("nhanThongTin");
            out.writeUTF(userName);

            System.err.println("Nhap ten nguoi dung muon ket noi");

            out.writeUTF("123");

            nhanTinNhan();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void guiNhanTinNhan(String tinNhan, String userGui, String userNhan) {
        this.userNhan = userNhan;
        try {
            System.out.println(tinNhan + "  " + userGui + "  " + userNhan);
            out.writeUTF("guiTinNhan");
            out.writeUTF(tinNhan);
            out.writeUTF(userGui);
            out.writeUTF(userNhan);

        } catch (Exception e) {

        }

    }

    public void layTinNhanTuServer(String userGui, String userNhan) {
        this.userNhan = userNhan;
        try {
            out.writeUTF("layTinNhanTuCSDL");
            out.writeUTF(userGui);
            out.writeUTF(userNhan);
        } catch (Exception e) {

        }

    }

    public void sendGetGroupListMessage() {
        try {
            // Send "getGroupList" message to the server
            out.writeUTF("getGroupList");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void layTinNhanNhomTuServer(String userName, String groupName) {
        userNhan = groupName;
        try {
            System.out.println(groupName);
            out.writeUTF("layTinNhanNhomTuCSDL");
            out.writeUTF(userName); // The user who wants to fetch group chat messages
            out.writeUTF(groupName); // The name of the group 
            clientGiaoDien.resetPanelXemTinNhan(userName, groupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guiTinNhanDenNhom(String message, String userName, String groupName) {
        this.userNhan = groupName;
        try {
            out.writeUTF("guiTinNhanNhom");
            out.writeUTF(message);
            out.writeUTF(userName);
            out.writeUTF(groupName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createGroupChat(String groupName) {
        try {
            out.writeUTF("createGroupChat");
            out.writeUTF(groupName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guiTepTin(String filePath, String userGui, String userNhan) {
        this.userNhan = userNhan;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("File not found: " + filePath);
                return;
            }

            long fileSizeInBytes = file.length();
            long maxFileSizeInBytes = 1024 * 1024; // 1MB in bytes

            if (fileSizeInBytes > maxFileSizeInBytes) {
                System.err.println("File size exceeds 1MB. File size: " + fileSizeInBytes + " bytes.");
                return;
            }

            String fileName = file.getName();
            String fileExtension = "";

            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
                fileExtension = fileName.substring(dotIndex + 1);
                fileName = fileName.substring(0, dotIndex);
            }

            System.out.println("Sending File: " + fileName + "." + fileExtension);
            System.out.println("Size: " + fileSizeInBytes + " bytes");
            System.out.println("From: " + userGui);
            System.out.println("To: " + userNhan);

            out.writeUTF("sendFile");
            out.writeUTF(userGui);
            out.writeUTF(userNhan);
            out.writeUTF(fileName);
            out.writeUTF(fileExtension);
            out.writeLong(fileSizeInBytes);
            // Read the file and send its content as bytes
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                System.out.println(bytesRead);
            }
            fis.close();
            out.flush();

            System.out.println("File sent successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> groupList;

    public void nhanTinNhan() {
        tinNhanNhan = "";

        Thread threadDocTinNhan;
        threadDocTinNhan = new Thread(new Runnable() {

            private String locTin;
            private String locTin1;
            private String time;
            private String tinNhan;
            private String user1;
            private String user2;
            private String locTin3;

            @Override
            public void run() {
                while (!tinNhanNhan.equals("exit")) {
                    try {
                        synchronized (in) {
                            tinNhanNhan = in.readUTF();
                            try {
                                locTin = tinNhanNhan.substring(0, 20);
                                locTin1 = tinNhanNhan.substring(0, 4);
                            } catch (Exception e) {

                            }
                            if (tinNhanNhan.equals("fileExists")) {
                                String fileNameFromServer = in.readUTF();
                                long size = in.readLong();
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setSelectedFile(new File(fileNameFromServer));
                                int userSelection = fileChooser.showSaveDialog(null);

                                if (userSelection == JFileChooser.APPROVE_OPTION) {
                                    File selectedFile = fileChooser.getSelectedFile();
                                    String selectedFileName = selectedFile.getAbsolutePath();

                                    String fileName = selectedFileName;

                                    FileOutputStream fos = new FileOutputStream(fileName);
                                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                                    byte[] buffer = new byte[4096];
                                    int bytesRead;
                                    long totalBytesRead = 0;
                                    System.out.println("File Size: " + size + " bytes");

                                    // Start receiving the file content
                                    while (totalBytesRead < size) {
                                        bytesRead = in.read(buffer);
                                        fos.write(buffer, 0, bytesRead);
                                        totalBytesRead += bytesRead;
                                    }

                                    bos.close();
                                    fos.close();

                                    System.out.println("File downloaded successfully: " + fileName);
                                }
                            } else if (tinNhanNhan.startsWith("groupList:")) {
                                tinNhanNhan = tinNhanNhan.replace("groupList:", "");
                                groupList = Arrays.asList(tinNhanNhan.split(","));
                                clientGiaoDien.updateGroupList(groupList);
                            } else if (locTin.equals("themNguoiDungVaoList")) {

                                tinNhanNhan = tinNhanNhan.replace("themNguoiDungVaoList ", "");

                                System.out.println("them " + tinNhanNhan);
                                clientGiaoDien.addListNguoiDungOnline(tinNhanNhan);

                            } else if (locTin.equals("xoaNguoiDungKhoiList")) {
                                tinNhanNhan = tinNhanNhan.replace("xoaNguoiDungKhoiList ", "");

                                System.out.println("xoa " + tinNhanNhan);

                                clientGiaoDien.removeList(tinNhanNhan);
                            } else if (locTin1.equals("Time")) {
                                try {
                                    time = tinNhanNhan.split("\\[")[1].split("]")[0];
                                    tinNhan = tinNhanNhan.split("TinNhan\\[")[1].split("]")[0];
                                    user1 = tinNhanNhan.split("user1\\[")[1].split("]")[0];
                                    user2 = tinNhanNhan.split("user2\\[")[1].split("]")[0];
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
                                String output = insertCharacter(tinNhan, "<br>", 46);

                                clientGiaoDien.addTinNhan(output, user1, user2, time);

                            } else if (tinNhanNhan.startsWith("resetMess00000000000-")) {
                                System.out.println(tinNhanNhan);
                                String[] tachTin = tinNhanNhan.split("-");
                                if (tachTin.length > 2) {
                                    String userGui = tachTin[1];
                                    String userNhan = tachTin[2];
                                        clientGiaoDien.resetPanelXemTinNhan(userGui, userNhan);
                                    
                                }
                            } else if (locTin.equals("themNguoiDungcu List")) {
                                tinNhanNhan = tinNhanNhan.replace("themNguoiDungcu List ", "");
                                clientGiaoDien.addListNguoiDungDaNhanTin(tinNhanNhan);
                            } else if (locTin.equals("GroupMessage0000Time")) {
                                // Process group chat messages with the format GroupMessage0000Time[timestamp] TinNhan[message] UserName[sender] GroupName[groupName]
                                String regex = "GroupMessage0000Time\\[(.*?)\\] TinNhan\\[(.*?)\\] UserName\\[(.*?)\\] GroupName\\[(.*?)\\]";
                                Pattern pattern = Pattern.compile(regex);
                                Matcher matcher = pattern.matcher(tinNhanNhan);

                                if (matcher.find()) {
                                    String time = matcher.group(1);
                                    String messageContent = matcher.group(2);
                                    String sender = matcher.group(3);
                                    String group = matcher.group(4);
                                    clientGiaoDien.addGroupChatMessage(messageContent, group, sender, time);
                                }

                            }

                        }

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }

            }
        });
        threadDocTinNhan.start();
    }

    // them ki tu <br> de tin nhan xuong hang
// Inside the ClientThread class, when processing group chat messages
    public void downloadFileFromServer(String fileName) {
        try {
            // Send a request to the server to download the specified file
            out.writeUTF("downloadFile");
            out.writeUTF(fileName);

        } catch (IOException e) {
            // Handle any exceptions that may occur during communication with the server
            e.printStackTrace();
        }
    }

    public static String insertCharacter(String input, String character, int interval) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            sb.append(input.charAt(i));
            count++;

            if (count == interval) {
                sb.append(character);
                count = 0;
            }
        }

        return sb.toString();
    }

    @Override
    public void run() {

        try {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

        } catch (IOException e1) {

            e1.printStackTrace();
        }

    }

}
