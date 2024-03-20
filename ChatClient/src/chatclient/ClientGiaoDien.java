package chatclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class ClientGiaoDien extends JFrame {

    private JPanel jPanel_dangNhap;
    private JPanel jPanel_tieuDe;
    private JTextField jTextField_nhapTaiKhoan;
    private JPasswordField jTextField_nhapMatKhau;
    private JTextField jTextField_tenDangNhap;
    private JPasswordField jTextField_matKhau;
    private JPasswordField jTextField_nhapLaiMatKhau;
    private String userName;
    private String passWord;
    private String passWordAgain;
    private JPanel jPanel_thanhBen;
    private JPanel jPanel_jlistVaLable;
    private JPanel jPanel_jlistVaLable3;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> listModel2;
    private DefaultListModel<String> listModel3;
    private ClientThread clientThread;
    private JList<String> jList_danhSachNguoiDung;
    private JList<String> jList_danhSachNguoiDungDaKetNoi;
    private String userNameNhanTinNhan;
    private JPanel jPanel_xemTinNhan;

    private JScrollPane scrollPane;
    private JPanel jPanel_listChat;
    private JPanel jPanel_jlistVaLable2;
    private Color mauDo;
    private Color mauXanh;
    private JLabel jLabel_nguoiDungDangChat;

    public void nhanThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    public ClientGiaoDien() {

    }

    public String getUserNameNhanTinNhan() {
        return userNameNhanTinNhan;
    }

    public void khoiDong() {

        this.setTitle("Phần mềm chat");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        jPanel_dangNhap = new JPanel();
        this.add(jPanel_dangNhap);
        this.setVisible(true);
        dangNhap();
    }

    public void dangNhap() {
        ActionListener ac = new ClientControler(this);

        jPanel_dangNhap.removeAll();

        jPanel_dangNhap.setBackground(new Color(156, 214, 189));
        jPanel_dangNhap.setBorder(new EmptyBorder(5, 5, 5, 5));
        jPanel_dangNhap.setLayout(null);

        JLabel lblNewLabel = new JLabel("Sign in to get started");
        lblNewLabel.setFont(new Font("STXinwei", Font.BOLD, 19));
        lblNewLabel.setBounds(174, 48, 200, 36);
        jPanel_dangNhap.add(lblNewLabel);

        jTextField_nhapTaiKhoan = new JTextField();
        jTextField_nhapTaiKhoan.setFont(new Font("Sylfaen", Font.PLAIN, 16));
        jTextField_nhapTaiKhoan.setBackground(new Color(204, 234, 221));
        jTextField_nhapTaiKhoan.setBounds(247, 109, 215, 36);
        jPanel_dangNhap.add(jTextField_nhapTaiKhoan);
        jTextField_nhapTaiKhoan.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("username");
        lblNewLabel_1.setFont(new Font("Sylfaen", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(140, 109, 96, 34);
        jPanel_dangNhap.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("password");
        lblNewLabel_2.setFont(new Font("Sylfaen", Font.PLAIN, 18));
        lblNewLabel_2.setBounds(151, 167, 72, 36);
        jPanel_dangNhap.add(lblNewLabel_2);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Sylfaen", Font.PLAIN, 19));
        btnLogin.setBackground(new Color(63, 152, 114));
        btnLogin.setBounds(197, 246, 131, 36);
        btnLogin.addActionListener(ac);
        jPanel_dangNhap.add(btnLogin);

        JButton btnRegis = new JButton("Register");
        btnRegis.setFont(new Font("Sylfaen", Font.PLAIN, 19));
        btnRegis.setBackground(new Color(63, 152, 114));
        btnRegis.setBounds(338, 246, 131, 36);
        jPanel_dangNhap.add(btnRegis);
        btnRegis.addActionListener(ac);
        jTextField_nhapMatKhau = new JPasswordField();
        jTextField_nhapMatKhau.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jTextField_nhapMatKhau.setBackground(new Color(204, 234, 221));
        jTextField_nhapMatKhau.setBounds(247, 166, 215, 36);
        jPanel_dangNhap.add(jTextField_nhapMatKhau);
        this.setVisible(true);
        jPanel_dangNhap.repaint();

    }

    public void dangKi() {

        ActionListener ac = new ClientControler(this);
        jPanel_dangNhap.removeAll();
        jPanel_dangNhap.repaint();
        jPanel_dangNhap.setLayout(null);
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(300, 24, 58, 44);
        jPanel_dangNhap.add(lblNewLabel);
        JLabel lblNewLabel_1 = new JLabel("Create new account");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(240, 70, 188, 44);
        jPanel_dangNhap.add(lblNewLabel_1);
        // form ten dang nhap
        jTextField_tenDangNhap = new JTextField();
        jTextField_tenDangNhap.setBackground(new Color(204, 234, 221));
        jTextField_tenDangNhap.setBounds(240, 124, 213, 32);
        jPanel_dangNhap.add(jTextField_tenDangNhap);
        jTextField_tenDangNhap.setColumns(10);
        JLabel lblNewLabel_2 = new JLabel("Name");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_2.setBounds(172, 124, 58, 32);
        jPanel_dangNhap.add(lblNewLabel_2);

        JLabel lblNewLabel_4 = new JLabel("Password");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_4.setBounds(159, 177, 81, 32);
        jPanel_dangNhap.add(lblNewLabel_4);
        jTextField_matKhau = new JPasswordField();
        jTextField_matKhau.setBackground(new Color(204, 234, 221));
        jTextField_matKhau.setBounds(240, 177, 213, 32);
        jPanel_dangNhap.add(jTextField_matKhau);
        jTextField_matKhau.setColumns(10);
        // form nhap lai mat khau

        JLabel lblNewLabel_5 = new JLabel("Confirm");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_5.setBounds(159, 227, 81, 32);
        jPanel_dangNhap.add(lblNewLabel_5);
        jTextField_nhapLaiMatKhau = new JPasswordField();
        jTextField_nhapLaiMatKhau.setBackground(new Color(204, 234, 221));
        jTextField_nhapLaiMatKhau.setBounds(240, 227, 213, 32);
        jPanel_dangNhap.add(jTextField_nhapLaiMatKhau);
        jTextField_nhapLaiMatKhau.setColumns(10);
        // button dang nhap
        //JButton jButton_dangNhap = new JButton("QUAY VỀ TRANG ĐĂNG NHẬP");
        //jPanel_dangNhap.add(jButton_dangNhap);
        //jButton_dangNhap.addActionListener(ac);
        //jButton_dangNhap.setBackground(Color.WHITE);

        // button dang kí
        JButton jButton_dangKi = new JButton("Save");
        jButton_dangKi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        jButton_dangKi.setBackground(new Color(63, 152, 114));
        jButton_dangKi.setBounds(200, 303, 128, 32);
        jButton_dangKi.addActionListener(ac);
        jPanel_dangNhap.add(jButton_dangKi);
        JButton jButton_troVe = new JButton("Return");
        jButton_troVe.setFont(new Font("Tahoma", Font.PLAIN, 18));
        jButton_troVe.setBackground(new Color(63, 152, 114));
        jButton_troVe.setBounds(350, 303, 128, 32);
        jButton_troVe.addActionListener(ac);
        jPanel_dangNhap.add(jButton_troVe);

    }

    // thong bao
    public void thongBao(String thongBao, Color mau) {
        JFrame jFrame_thongBao = new JFrame();
        jFrame_thongBao.setSize(300, 160);
        jFrame_thongBao.setTitle("Thông báo");
        jFrame_thongBao.setLocationRelativeTo(null);
        jFrame_thongBao.setLayout(new BorderLayout());
        //
        JLabel jLabel_thongBao = new JLabel(thongBao, JLabel.CENTER);
        jLabel_thongBao.setForeground(mau);
        Font font = new Font("Arial", Font.BOLD, 15);
        jLabel_thongBao.setFont(font);
        jFrame_thongBao.add(jLabel_thongBao, BorderLayout.CENTER);
        jFrame_thongBao.setVisible(true);

    }

// phan xu ly dang nhap
    public void xuLyDangNhap() {
        userName = jTextField_nhapTaiKhoan.getText();
        passWord = new String(jTextField_nhapMatKhau.getPassword());
        clientThread.xuLyDangNhap(userName, passWord);

    }

    public void thongBaoDangNhapThanhCong() {

        this.dispose();
        Color mauXanh = new Color(90, 196, 70);
        thongBao("Dang nhap thanh cong!", mauXanh);

    }

    public void thongBaoDangNhapKhongThanhCong() {

        Color mauDo = new Color(255, 0, 0);
        thongBao("Sai tai khoan hoac mat khau !", mauDo);

    }

    // phan xu ly dang ki
    public void xuLyDangKi() {
        userName = jTextField_tenDangNhap.getText();
        passWord = new String(jTextField_matKhau.getPassword());
        passWordAgain = new String(jTextField_nhapLaiMatKhau.getPassword());
        if (passWord.equals(passWordAgain)) {
            clientThread.xuLyDangKy(userName, passWord);

        } else {
            Color mauDo = new Color(255, 0, 0);
            thongBao("Nhap lai mat khau sai !", mauDo);
        }

    }

    public void thongBaoDangKiThanhCong() {
        mauXanh = new Color(90, 196, 70);
        thongBao("Dang ki thanh cong!", mauXanh);

    }

    public void thongBaoDangKiKhongThanhCong() {
        Color mauDo = new Color(255, 0, 0);
        thongBao("username da ton tai !", mauDo);

    }

    // giao dien nguoi dung
    public void hienThiDanhSachNguoiDung() {
        JLabel jLabel_danhSachNguoiDung = new JLabel("USER ONLINE");
        Font font = new Font("Arial", Font.BOLD, 20);
        jLabel_danhSachNguoiDung.setFont(font);

        listModel = new DefaultListModel<>();

        jList_danhSachNguoiDung = new JList<>(listModel);
        jList_danhSachNguoiDung.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the clicked JList
                JList<String> clickedList = (JList<String>) e.getSource();

                // Clear selection for other JLists
                if (clickedList == jList_danhSachNguoiDung) {
                    jList_danhSachNhom.clearSelection();
                    jList_danhSachNguoiDungDaKetNoi.clearSelection();
                    layTinNhanTuCsdl();
                }

            }
        });
        JScrollPane jc = new JScrollPane(jPanel_jlistVaLable);

        jList_danhSachNguoiDung.setFont(font);
        jPanel_jlistVaLable.setBackground(new Color(176, 190, 197));
        jPanel_jlistVaLable.add(jLabel_danhSachNguoiDung, BorderLayout.NORTH);
        jPanel_jlistVaLable.add(jList_danhSachNguoiDung, BorderLayout.CENTER);
        jPanel_listChat.add(jc);

    }

    public void hienThiDanhSachNguoiDungDaKetNoi() {
        JLabel jLabel_danhSachNguoiDung = new JLabel("USER ĐÃ NHẮN TIN");
        mauDo = new Color(255, 0, 0);
        Font font = new Font("Arial", Font.BOLD, 20);
        jLabel_danhSachNguoiDung.setFont(font);

        listModel2 = new DefaultListModel<>();

        jList_danhSachNguoiDungDaKetNoi = new JList<>(listModel2);
        jList_danhSachNguoiDungDaKetNoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the clicked JList
                JList<String> clickedList = (JList<String>) e.getSource();

                // Clear selection for other JLists
                if (clickedList == jList_danhSachNguoiDungDaKetNoi) {
                    jList_danhSachNhom.clearSelection();
                    jList_danhSachNguoiDung.clearSelection();
                    layTinNhanTuCsdl();
                }

            }
        });
        JScrollPane jc = new JScrollPane(jPanel_jlistVaLable2);
        jList_danhSachNguoiDungDaKetNoi.setFont(font);

        jPanel_jlistVaLable2.setBackground(new Color(176, 190, 197));
        jPanel_jlistVaLable2.add(jLabel_danhSachNguoiDung, BorderLayout.NORTH);
        jPanel_jlistVaLable2.add(jList_danhSachNguoiDungDaKetNoi, BorderLayout.CENTER);
        jPanel_listChat.add(jc);
    }
    private JList jList_danhSachNhom;

    public void hienThiDanhSachNhom() {
        JLabel jLabel_danhSachNhom = new JLabel("DANH SÁCH NHÓM");
        mauDo = new Color(255, 0, 0);
        Font font = new Font("Arial", Font.BOLD, 20);
        jLabel_danhSachNhom.setFont(font);

        listModel3 = new DefaultListModel<>();

        jList_danhSachNhom = new JList<>(listModel3);
        jList_danhSachNhom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the clicked JList
                JList<String> clickedList = (JList<String>) e.getSource();

                // Clear selection for other JLists
                if (clickedList == jList_danhSachNhom) {
                    jList_danhSachNguoiDungDaKetNoi.clearSelection();
                    jList_danhSachNguoiDung.clearSelection();
                    if (listModel3.size() > 0) {
                        layTinNhanTuCsdl();
                    }
                }
            }
        });
        JScrollPane jc3 = new JScrollPane(jPanel_jlistVaLable3);
        jList_danhSachNhom.setFont(font);

        jPanel_jlistVaLable3.setBackground(new Color(176, 190, 197));
        jPanel_jlistVaLable3.add(jLabel_danhSachNhom, BorderLayout.NORTH);
        jPanel_jlistVaLable3.add(jList_danhSachNhom, BorderLayout.CENTER);
        jPanel_listChat.add(jc3);
    }

    public void addListNguoiDungOnline(String user) {
        if (user.equals(userName)) {
            return;
        }

        if (!listModel.contains(user)) {
            listModel.addElement(user);

        } else {
            System.err.println("người dùng đã tồn tại" + user);
        }

    }

    public void updateGroupList(List<String> groupList) {
        listModel3.clear();
        for (int i = 0; i < groupList.size(); i++) {
            listModel3.addElement(groupList.get(i));
        }
    }

    public void addListNguoiDungDaNhanTin(String user) {
        if (user.equals(userName)) {
            return;
        }

        if (!listModel2.contains(user)) {
            listModel2.addElement(user);

        } else {
            System.err.println("người dùng đã tồn tại" + user);
        }

    }

    public void removeList(String user) {
        listModel.removeElement(user);
    }

    public void guiTinNhan() {
        String tinNhan = layNoiDungTextPaneNhapTinNhan();
        jTextFiledNhapTinNhan.setText("");
        if (userNameNhanTinNhan != null) {
            if (userNameNhanTinNhan.startsWith("#")) {
                // Gửi tin nhắn đến nhóm
                clientThread.guiTinNhanDenNhom(tinNhan, userName, userNameNhanTinNhan.replace("#", ""));
            } else {
                // Gửi tin nhắn đến người dùng khác
                clientThread.guiNhanTinNhan(tinNhan, userName, userNameNhanTinNhan);
            }
        } else {
            thongBao("Vui lòng chọn người chat hoặc nhóm chat", Color.RED);
        }
    }

    public void layTinNhanTuCsdl() {
        if (jList_danhSachNguoiDung.getSelectedValue() == null
                && jList_danhSachNguoiDungDaKetNoi.getSelectedValue() == null
                && jList_danhSachNhom.getSelectedValue() == null) {
            thongBao("Vui lòng chọn người chat hoặc nhóm chat", mauDo);
        } else if (jList_danhSachNguoiDung.getSelectedValue() != null
                || jList_danhSachNguoiDungDaKetNoi.getSelectedValue() != null) {
            if (jList_danhSachNguoiDungDaKetNoi.getSelectedValue() == null) {
                this.userNameNhanTinNhan = jList_danhSachNguoiDung.getSelectedValue();
                jLabel_nguoiDungDangChat.setText("BẠN ĐANG CHAT VỚI: " + this.userNameNhanTinNhan);
            } else {
                this.userNameNhanTinNhan = jList_danhSachNguoiDungDaKetNoi.getSelectedValue();
                jLabel_nguoiDungDangChat.setText("BẠN ĐANG CHAT VỚI: " + this.userNameNhanTinNhan);
            }
            clientThread.layTinNhanTuServer(userName, this.userNameNhanTinNhan);
        } else if (jList_danhSachNhom.getSelectedValue() != null) {
            String groupName = jList_danhSachNhom.getSelectedValue().toString();
            this.userNameNhanTinNhan = "#" + groupName;
            jLabel_nguoiDungDangChat.setText("BẠN ĐANG CHAT VỚI: " + userNameNhanTinNhan);

            clientThread.layTinNhanNhomTuServer(userName, groupName);
        }
    }

    public void resetPanelXemTinNhan(String userGui, String userNhan) {
        if (userNameNhanTinNhan == null) {
            return;
        }
        if (userGui.equals(userName) && userNhan.replace("#", "").equals(userNameNhanTinNhan.replace("#", ""))) {
            jPanel_xemTinNhan.removeAll();
            jPanel_xemTinNhan.revalidate();
            jPanel_xemTinNhan.repaint();
        }

    }

    public void addTinNhan(String tinNhan, String user1, String user2, String time) {
        if (user2.equals(userNameNhanTinNhan) || user1.equals(userNameNhanTinNhan)) {
            if (user1.equals(userName)) {
                JPanel jPanel_tinNhan = new JPanel();
                JLabel jLabel_tinNhan;

                if (tinNhan.startsWith("icon:")) {
                    String iconPath = tinNhan.substring(5); // Remove "icon:" prefix
                    ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                    String messageText = "<html><body style='width: 400px;max-width:400px; margin: 5px;color:rgb(242, 242,248);border: 5px solid #1565C0; font-size: 12px; background-color:rgb(242,242,248);'><div style='background-color: #1565C0;'>"
                            + "<img src='" + getClass().getResource(iconPath) + "'/>"
                            + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time + "</div></div></body></html>";
                    jLabel_tinNhan = new JLabel(messageText);
                } else if (tinNhan.startsWith("file:")) {
                    String fileName = tinNhan.substring(5); // Remove "file:" prefix
                    jLabel_tinNhan = new JLabel(
                            "<html><body style='width: 400px;max-width:400px; margin: 5px;border: 5px solid #1565C0; font-size: 12px; text-decoration: underline; color: violet; cursor: hand; background-color:rgb(242,242,248);'><div style='background-color: #1565C0;'>"
                            + fileName + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                            + "</div></div></body></html>");
                    jLabel_tinNhan.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Trigger the event to download the file from the server
                            clientThread.downloadFileFromServer(fileName);
                        }
                    });
                } else {
                    jLabel_tinNhan = new JLabel(
                            "<html><body style='width: 400px;max-width:400px;color:rgb(242, 242,248); margin: 5px;border: 5px solid #1565C0; font-size: 12px; background-color:rgb(242,242,248);'><div style='background-color: #1565C0;'>"
                            + tinNhan + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                            + "</div></div></body></html>");
                }

                Color mau1 = new Color(242, 242, 248);
                jPanel_tinNhan.setBackground(mau1);
                jPanel_tinNhan.setLayout(new GridLayout(1, 3));
                JPanel jPanel_tam = new JPanel();

                jPanel_tinNhan.add(jPanel_tam);
                jPanel_tam.setBackground(mau1);
                jPanel_tinNhan.add(jLabel_tinNhan);

                jPanel_xemTinNhan.add(jPanel_tinNhan, 0);

                jPanel_tinNhan.setBackground(mau1);
                // luot jscrollpane xuong duoi cung
                int height = (int) jPanel_xemTinNhan.getPreferredSize().getHeight();
                Rectangle rect = new Rectangle(0, height, 10, 10);
                jPanel_xemTinNhan.scrollRectToVisible(rect);
            } else {
                JPanel jPanel_tinNhan = new JPanel();
                JLabel jLabel_tinNhan;

                if (tinNhan.startsWith("icon:")) {
                    String iconPath = tinNhan.substring(5); // Remove "icon:" prefix
                    ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                    jLabel_tinNhan = new JLabel(
                            "<html><body style='width: 400px;max-width:400px; margin: 5px; border: 5px solid #CFD8DB; ont-size: 12px;font-size: 12px;'><div style='background-color: #CFD8DB;'>"
                            + "<img src='" + getClass().getResource(iconPath) + "'/>"
                            + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                            + "</div></div></body></html>");
                } else if (tinNhan.startsWith("file:")) {
                    String fileName = tinNhan.substring(5); // Remove "file:" prefix
                    jLabel_tinNhan = new JLabel(
                            "<html><body style='width: 400px;max-width:400px; margin: 5px; border: 5px solid #CFD8DB; font-size: 12px; text-decoration: underline; color: blue; cursor: hand;'><div style='background-color: #CFD8DB;'>"
                            + fileName + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                            + "</div></div></body></html>");
                    jLabel_tinNhan.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Trigger the event to download the file from the server
                            clientThread.downloadFileFromServer(fileName);
                        }
                    });
                } else {
                    jLabel_tinNhan = new JLabel(
                            "<html><body style='width: 400px;max-width:400px; margin: 5px; border: 5px solid #CFD8DB; black; font-size: 12px;'><div style='background-color: #CFD8DB;font-size: 12px;'>"
                            + tinNhan + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                            + "</div></div></body></html>");
                }

                Color mau1 = new Color(242, 242, 248);

                jPanel_tinNhan.setLayout(new GridLayout(1, 2));
                JPanel jPanel_tam = new JPanel();
                jPanel_tam.setBackground(mau1);
                jPanel_tinNhan.add(jLabel_tinNhan);
                jPanel_tinNhan.add(jPanel_tam);

                jPanel_xemTinNhan.add(jPanel_tinNhan, 0);

                jPanel_tinNhan.setBackground(mau1);
                // luot jscrollpane xuong duoi cung
                int height = (int) jPanel_xemTinNhan.getPreferredSize().getHeight();
                Rectangle rect = new Rectangle(0, height, 10, 10);
                jPanel_xemTinNhan.scrollRectToVisible(rect);
            }
        }
    }

    public void addGroupChatMessage(String tinNhan, String groupName, String user, String time) {
        if (userNameNhanTinNhan == null) {
            return;
        }
        if (!userNameNhanTinNhan.replace("#", "").equals(groupName)) {
            return;
        }
        if (user.equals(userName)) {
            JPanel jPanel_tinNhan = new JPanel();
            JLabel jLabel_tinNhan;

            if (tinNhan.startsWith("icon:")) {
                String iconPath = tinNhan.substring(5); // Remove "icon:" prefix
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                String messageText = "<html><body style='width: 400px;max-width:400px; margin: 5px;color:rgb(242, 242,248);border: 5px solid #1565C0; font-size: 12px; background-color:rgb(242,242,248);'><div style='background-color: #1565C0;'>"
                        + "<img src='" + getClass().getResource(iconPath) + "'/>"
                        + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time + "</div></div></body></html>";
                jLabel_tinNhan = new JLabel(messageText);
            } else if (tinNhan.startsWith("file:")) {
                String fileName = tinNhan.substring(5); // Remove "file:" prefix
                jLabel_tinNhan = new JLabel(
                        "<html><body style='width: 400px;max-width:400px; margin: 5px;border: 5px solid #1565C0; font-size: 12px; text-decoration: underline; color: violet; cursor: hand; background-color:rgb(242,242,248);'><div style='background-color: #1565C0;'>"
                       + fileName + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                        + "</div></div></body></html>");
                jLabel_tinNhan.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Trigger the event to download the file from the server
                        clientThread.downloadFileFromServer(fileName);
                    }
                });
            } else {
                jLabel_tinNhan = new JLabel(
                        "<html><body style='width: 400px;max-width:400px;color:rgb(242, 242,248); margin: 5px;border: 5px solid #1565C0; font-size: 12px; background-color:rgb(242,242,248);'><div style='background-color: #1565C0;'>"
                       + tinNhan + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                        + "</div></div></body></html>");
            }
            Color mau1 = new Color(242, 242, 248);
            jPanel_tinNhan.setBackground(mau1);
            jPanel_tinNhan.setLayout(new GridLayout(1, 2));
            JPanel jPanel_tam = new JPanel();

            jPanel_tinNhan.add(jPanel_tam);
            jPanel_tam.setBackground(mau1);
            jPanel_tinNhan.add(jLabel_tinNhan);

            jPanel_xemTinNhan.add(jPanel_tinNhan, 0);

            jPanel_tinNhan.setBackground(mau1);
            // luot jscrollpane xuong duoi cung
            int height = (int) jPanel_xemTinNhan.getPreferredSize().getHeight();
            Rectangle rect = new Rectangle(0, height, 10, 10);
            jPanel_xemTinNhan.scrollRectToVisible(rect);
        } else {
            JPanel jPanel_tinNhan = new JPanel();
            JLabel jLabel_tinNhan;

            if (tinNhan.startsWith("icon:")) {
                String iconPath = tinNhan.substring(5); // Remove "icon:" prefix
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                jLabel_tinNhan = new JLabel(
                        "<html><body style='width: 400px;max-width:400px; margin: 5px; border: 5px solid #CFD8DB; ont-size: 12px;font-size: 12px;'><div style='background-color: #CFD8DB;'>"
                        +user + ": "  + "<img src='" + getClass().getResource(iconPath) + "'/>"
                        + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                        + "</div></div></body></html>");
            } else if (tinNhan.startsWith("file:")) {
                String fileName = tinNhan.substring(5); // Remove "file:" prefix
                jLabel_tinNhan = new JLabel(
                        "<html><body style='width: 400px;max-width:400px; margin: 5px; border: 5px solid #CFD8DB; font-size: 12px; text-decoration: underline; color: blue; cursor: hand;'><div style='background-color: #CFD8DB;'>"
                         +user + ": " + fileName + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                        + "</div></div></body></html>");
                jLabel_tinNhan.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Trigger the event to download the file from the server
                        clientThread.downloadFileFromServer(fileName);
                    }
                });
            } else {
                jLabel_tinNhan = new JLabel(
                        "<html><body style='width: 400px;max-width:400px; margin: 5px; border: 5px solid #CFD8DB; black; font-size: 12px;'><div style='background-color: #CFD8DB;font-size: 12px;'>"
                         +user + ": " + tinNhan + "<div style='font-size: 12px;width: 400px;max-width:400px;'>" + time
                        + "</div></div></body></html>");
            }

            Color mau1 = new Color(242, 242, 248);

            jPanel_tinNhan.setLayout(new GridLayout(1, 2));
            JPanel jPanel_tam = new JPanel();
            jPanel_tam.setBackground(mau1);
            jPanel_tinNhan.add(jLabel_tinNhan);
            jPanel_tinNhan.add(jPanel_tam);

            jPanel_xemTinNhan.add(jPanel_tinNhan, 0);

            jPanel_tinNhan.setBackground(mau1);
            // luot jscrollpane xuong duoi cung
            int height = (int) jPanel_xemTinNhan.getPreferredSize().getHeight();
            Rectangle rect = new Rectangle(0, height, 10, 10);
            jPanel_xemTinNhan.scrollRectToVisible(rect);
        }
    }

    public void createGroupChat() {
        String groupName = JOptionPane.showInputDialog(this, "Enter the group name:");

        if (groupName != null && !groupName.trim().isEmpty()) {
            clientThread.createGroupChat(groupName);
            clientThread.sendGetGroupListMessage(); // Send "getGroupList" message to the server
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid group name.");
        }
  }
   private static Icon createCustomSmileyIcon(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x, y, size, size);

                int eyeSize = size / 5;
                int mouthSize = size / 2;
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + size / 4, y + size / 4, eyeSize, eyeSize);
                g2d.fillOval(x + size / 2, y + size / 4, eyeSize, eyeSize);
                g2d.drawArc(x + size / 4, y + size / 4, size / 2, size / 2, 180, -180);
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }
    private ImageIcon selectedIcon;
    private JTextField jTextFiledNhapTinNhan;

    public void trangNguoiDung() {
        Color mau1 = new Color(242, 242, 242);

        ActionListener ac = new ClientControler(this);
        JFrame jFrame_trangNguoiDung = new JFrame();
        jFrame_trangNguoiDung.setSize(1296, 720);
        jFrame_trangNguoiDung.setResizable(false);
        jFrame_trangNguoiDung.setTitle("CHAT APP USER : " + userName);
        jFrame_trangNguoiDung.setLocationRelativeTo(null);
        jFrame_trangNguoiDung.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame_trangNguoiDung.setLayout(new BorderLayout());
        jFrame_trangNguoiDung.setVisible(true);

        // panel trang chinh
        JPanel jPanel_trangChinh = new JPanel();
        jFrame_trangNguoiDung.add(jPanel_trangChinh, BorderLayout.CENTER);
        jPanel_trangChinh.setBackground(new Color(242, 242, 248));
        jPanel_trangChinh.setLayout(new BorderLayout());

        // jpanel thanh ben (hien thi danh sach nguoi dung online)
        jPanel_thanhBen = new JPanel();
        jFrame_trangNguoiDung.add(jPanel_thanhBen, BorderLayout.WEST);
        jPanel_thanhBen.setBackground(Color.white);

        jPanel_thanhBen.setPreferredSize(new Dimension(200, 720));
        jPanel_thanhBen.setLayout(new BorderLayout());
        jPanel_listChat = new JPanel();
        jPanel_thanhBen.add(jPanel_listChat, BorderLayout.CENTER);
        jPanel_listChat.setLayout(new GridLayout(3, 1));
        JButton jButton_taoNhomChat = new JButton("TẠO NHÓM CHAT");
        jPanel_thanhBen.add(jButton_taoNhomChat, BorderLayout.NORTH);
        jButton_taoNhomChat.setPreferredSize(new Dimension(200, 50));
        jButton_taoNhomChat.addActionListener(ac);
        // button chon nguoi de chat
        //JButton jButton_nutChonNguoi = new JButton("CHỌN");
        //jButton_nutChonNguoi.addActionListener(ac);
        //jPanel_thanhBen.add(jButton_nutChonNguoi, BorderLayout.SOUTH);
        // jButton_nutChonNguoi.setPreferredSize(new Dimension(200, 50));

        // jlist
        jPanel_jlistVaLable = new JPanel();
        jPanel_jlistVaLable.setLayout(new BorderLayout());

        jPanel_jlistVaLable2 = new JPanel();
        jPanel_jlistVaLable2.setLayout(new BorderLayout());
        jPanel_jlistVaLable3 = new JPanel();
        jPanel_jlistVaLable3.setLayout(new BorderLayout());
        // jlable
        hienThiDanhSachNhom();
        hienThiDanhSachNguoiDung();
        hienThiDanhSachNguoiDungDaKetNoi();

        // jpanel nut thao tac
        JPanel jPanel_chat = new JPanel();
        jPanel_chat.setLayout(new BorderLayout());
        jPanel_trangChinh.add(jPanel_chat, BorderLayout.SOUTH);
        JPanel jPanel_thaoTacChat = new JPanel();
        jPanel_chat.add(jPanel_thaoTacChat, BorderLayout.CENTER);
        jPanel_thaoTacChat.setBackground(new Color(242, 242, 248));
        jPanel_thaoTacChat.setPreferredSize(new Dimension(1080, 50));

        jPanel_thaoTacChat.setLayout(null);

        jTextFiledNhapTinNhan = new JTextField();
        jTextFiledNhapTinNhan.setFont(new Font("Arial", 30, 20));
        jTextFiledNhapTinNhan.setBounds(0, 0, 880, 50);
        jTextFiledNhapTinNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hàm xử lý khi người dùng nhấn phím Enter
                guiTinNhan();
            }
        });
        jPanel_thaoTacChat.add(jTextFiledNhapTinNhan);
        //JButton jButton_guiTinNhan = new JButton("GỬI");
        //jButton_guiTinNhan.setBackground(new Color(88, 255, 155));
        //jPanel_chat.add(jButton_guiTinNhan, BorderLayout.EAST);
        //jButton_guiTinNhan.setPreferredSize(new Dimension(100, 50));
       // jButton_guiTinNhan.addActionListener(ac);
        JButton jButton_chonIcon = new JButton();
        Icon smileyIcon = createCustomSmileyIcon(20);
        jButton_chonIcon.setIcon(smileyIcon);
        jPanel_thaoTacChat.add(jButton_chonIcon);
        jButton_chonIcon.setBounds(880, 0, 100, 50);
        jButton_chonIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userNameNhanTinNhan == null) {
                    JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn người để chat", "Thông báo", HEIGHT, null);
                    return;
                }
                showIconSelectionDialog();
            }
        });
        JButton jButton_chonTepTin = new JButton();
        jButton_chonTepTin.setIcon(UIManager.getIcon("FileView.fileIcon"));
        Font font1 = new Font("sans-serif", Font.ITALIC, 30);
        jButton_chonTepTin.setFont(font1);
        jPanel_thaoTacChat.add(jButton_chonTepTin);
        
        jButton_chonTepTin.setBounds(980, 0, 100, 50);
        jButton_chonTepTin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userNameNhanTinNhan == null) {
                    JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn người để chat", "Thông báo", HEIGHT, null);
                    return;
                }
                showFileSelectionDialog();
            }
        });
        // jpanel xem tin nhan
        jPanel_xemTinNhan = new JPanel();
        scrollPane = new JScrollPane(jPanel_xemTinNhan);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel_trangChinh.add(scrollPane, BorderLayout.CENTER);
        jPanel_xemTinNhan.setLayout(new BoxLayout(jPanel_xemTinNhan, BoxLayout.Y_AXIS));
        jPanel_xemTinNhan.setBackground(mau1);

        // panel hien thi nguoi dung dang chat
        JPanel jPanel_nguoiDungDangChat = new JPanel();
        jPanel_trangChinh.add(jPanel_nguoiDungDangChat, BorderLayout.NORTH);
        jPanel_nguoiDungDangChat.setLayout(new BorderLayout());
        jPanel_nguoiDungDangChat.setBackground(new Color(176, 190, 197));
        jLabel_nguoiDungDangChat = new JLabel("BẠN ĐANG CHAT VỚI: ");
        Font font = new Font("Arial", Font.BOLD, 20);
        jLabel_nguoiDungDangChat.setFont(font);
        jPanel_nguoiDungDangChat.add(jLabel_nguoiDungDangChat, BorderLayout.WEST);
        jLabel_nguoiDungDangChat.setHorizontalAlignment(JLabel.CENTER);
        jLabel_nguoiDungDangChat.setPreferredSize(new Dimension(1080, 50));
        jFrame_trangNguoiDung.setVisible(true);

    }

    public void showFileSelectionDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            String userGui = userName;
            String userNhan = userNameNhanTinNhan;

            clientThread.guiTepTin(filePath, userGui, userNhan);

        }
    }

    public String layNoiDungTextPaneNhapTinNhan() {
        Document doc = jTextFiledNhapTinNhan.getDocument();
        try {
            return doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void xoaNoiDungTextPaneNhapTinNhan() {
        jTextFiledNhapTinNhan.setText("");
    }

    private void showIconSelectionDialog() {
        List<ImageIcon> iconList = new ArrayList<>();
        for (int i = 1; i <= 134; i++) {
            String iconName = "/icons/" + i + ".gif";
            ImageIcon icon = new ImageIcon(getClass().getResource(iconName));
            icon.setDescription(iconName);
            iconList.add(icon);
        }

        JPanel jPanel_iconGrid = new JPanel(new GridLayout(14, 10));
        JDialog dialog = new JDialog();
        dialog.setTitle("Chọn Icon");
        for (ImageIcon icon : iconList) {
            JButton iconButton = new JButton(icon);
            iconButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String iconPath = icon.getDescription();
                    sendTinNhanWithIcon(iconPath);
                    dialog.dispose();
                }
            });
            jPanel_iconGrid.add(iconButton);
        }

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.add(jPanel_iconGrid);
        dialog.pack();

        // Center the dialog on the main interface
        dialog.setLocationRelativeTo(null);

        dialog.setVisible(true);
    }

    public void sendTinNhanWithIcon(String iconPath) {
        if (iconPath != null) {
            if (userNameNhanTinNhan != null) {
                if (userNameNhanTinNhan.startsWith("#")) {
                    clientThread.guiTinNhanDenNhom("icon:" + iconPath, userName, userNameNhanTinNhan.replace("#", ""));
                } else {
                    clientThread.guiNhanTinNhan("icon:" + iconPath, userName, userNameNhanTinNhan);
                }
            } else {
                thongBao("Vui lòng chọn người chat hoặc nhóm chat", Color.RED);
            }
        } else {
            thongBao("Vui long chon icon", Color.RED);
        }

    }

}
